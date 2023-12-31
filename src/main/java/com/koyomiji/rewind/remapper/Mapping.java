package com.koyomiji.rewind.remapper;

import com.google.gson.Gson;
import com.koyomiji.rewind.remapper.json.JsonMapping;
import java.util.*;
import org.objectweb.asm.Type;

public class Mapping implements Cloneable {
  public Map<String, String> classes;
  public Map<MemberIdentifier, String> methods;
  public Map<MemberIdentifier, String> fields;
  public Map<MemberIdentifier, String[]> paramNames;

  public Mapping() {
    this(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
  }

  public Mapping(Map<String, String> classes,
                 Map<MemberIdentifier, String> methods,
                 Map<MemberIdentifier, String> fields,
                 Map<MemberIdentifier, String[]> paramNames) {
    this.classes = classes;
    this.methods = methods;
    this.fields = fields;
    this.paramNames = paramNames;
  }

  public String mapClassName(String className) {
    return classes.get(className);
  }

  public String unmapClassName(String className) {
    return MapHelper.getKey(classes, className);
  }

  public Type mapType(Type type) {
    if (type.getSort() == Type.OBJECT) {
      String className = TypeHelper.getInternalClassName(type);
      String mappedClassName = mapClassName(className);

      if (mappedClassName != null) {
        return TypeHelper.getClassType(mappedClassName);
      }
    } else if (type.getSort() == Type.ARRAY) {
      Type elementType = type.getElementType();
      Type mappedElementType = mapType(elementType);

      return TypeHelper.getArrayType(type.getDimensions(), mappedElementType);
    }

    return type;
  }

  public Type unmapType(Type type) {
    if (type.getSort() == Type.OBJECT) {
      String className = TypeHelper.getInternalClassName(type);
      String unmappedClassName = unmapClassName(className);

      if (unmappedClassName != null) {
        return TypeHelper.getClassType(unmappedClassName);
      }
    } else if (type.getSort() == Type.ARRAY) {
      Type elementType = type.getElementType();
      Type unmappedElementType = unmapType(elementType);

      return TypeHelper.getArrayType(type.getDimensions(), unmappedElementType);
    }

    return type;
  }

  private String mapMethodDescriptor(String desc) {
    Type rType = Type.getReturnType(desc);
    Type[] pTypes = Type.getArgumentTypes(desc);

    StringBuilder mappedDesc = new StringBuilder("(");

    for (Type pType : pTypes) {
      mappedDesc.append(mapType(pType).getDescriptor());
    }

    mappedDesc.append(")").append(mapType(rType).getDescriptor());
    return mappedDesc.toString();
  }

  public String mapDescriptor(String desc) {
    if (desc.startsWith("(")) {
      return mapMethodDescriptor(desc);
    }

    Type type = Type.getType(desc);
    return mapType(type).getDescriptor();
  }

  private String unmapMethodDescriptor(String desc) {
    Type rType = Type.getReturnType(desc);
    Type[] pTypes = Type.getArgumentTypes(desc);

    StringBuilder unmappedDesc = new StringBuilder("(");

    for (Type pType : pTypes) {
      unmappedDesc.append(unmapType(pType).getDescriptor());
    }

    unmappedDesc.append(")").append(unmapType(rType).getDescriptor());
    return unmappedDesc.toString();
  }

  public String unmapDescriptor(String desc) {
    if (desc.startsWith("(")) {
      return unmapMethodDescriptor(desc);
    }

    Type type = Type.getType(desc);
    return unmapType(type).getDescriptor();
  }

  public String mapMethodName(MemberIdentifier method) {
    return mapMethodName(method.owner, method.name, method.desc);
  }

  public String mapMethodName(String owner, String name, String desc) {
    MemberIdentifier id = new MemberIdentifier(owner, name, desc);
    return methods.get(id);
  }

  public MemberIdentifier unmapMethodName(MemberIdentifier method) {
    return unmapMethodName(method.owner, method.name, method.desc);
  }

  public MemberIdentifier unmapMethodName(String owner, String name,
                                          String desc) {
    String oldOwner = unmapClassName(owner);
    String oldDesc = unmapDescriptor(desc);

    for (Map.Entry<MemberIdentifier, String> entry : methods.entrySet()) {
      if (entry.getValue().equals(name) &&
          entry.getKey().owner.equals(oldOwner) &&
          entry.getKey().desc.equals(oldDesc)) {
        return entry.getKey();
      }
    }

    // FIXME: Inconsistent
    return new MemberIdentifier(oldOwner, name, oldDesc);
  }

  public String mapFieldName(MemberIdentifier field) {
    return mapFieldName(field.owner, field.name);
  }

  public String mapFieldName(String owner, String name) {
    MemberIdentifier id = new MemberIdentifier(owner, name, null);
    return fields.get(id);
  }

  public MemberIdentifier unmapFieldName(MemberIdentifier field) {
    return unmapFieldName(field.owner, field.name);
  }

  public MemberIdentifier unmapFieldName(String owner, String name) {
    String oldOwner = unmapClassName(owner);

    for (Map.Entry<MemberIdentifier, String> entry : fields.entrySet()) {
      if (entry.getValue().equals(name) &&
          entry.getKey().owner.equals(oldOwner)) {
        return entry.getKey();
      }
    }

    return null;
  }

  public String[] getParamNames(MemberIdentifier method) {
    return paramNames.get(method);
  }

  public String getParamNameBySizedIndex(MemberIdentifier method,
                                         int sizedIndex,
                                         boolean isStaticMethod) {
    String[] paramNames = getParamNames(method);

    if (paramNames == null) {
      return null;
    }

    List<Type> params = new ArrayList<>();

    if (!isStaticMethod) {
      params.add(Type.getType("L" + method.owner + ";"));
    }

    params.addAll(Arrays.asList(Type.getArgumentTypes(method.desc)));
    int index = TypeHelper.sizedIndexToIndex(params, sizedIndex);

    if (!isStaticMethod) {
      index -= 1;
    }

    if (index < 0 || index >= paramNames.length) {
      return null;
    }

    return paramNames[index];
  }

  public String toJSON() {
    Gson gson = new Gson();
    return gson.toJson(JsonMapping.from(this));
  }

  public static Mapping fromJSON(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, JsonMapping.class).to();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Mapping mapping = (Mapping)o;
    return Objects.equals(classes, mapping.classes) &&
        Objects.equals(methods, mapping.methods) &&
        Objects.equals(fields, mapping.fields) &&
        Objects.equals(paramNames, mapping.paramNames);
  }

  @Override
  public int hashCode() {
    return Objects.hash(classes, methods, fields, paramNames);
  }

  @Override
  public Mapping clone() {
    try {
      Mapping clone = (Mapping)super.clone();
      clone.classes = new HashMap<>(classes);
      clone.methods = new HashMap<>(methods);
      clone.fields = new HashMap<>(fields);
      clone.paramNames = new HashMap<>(paramNames);
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
