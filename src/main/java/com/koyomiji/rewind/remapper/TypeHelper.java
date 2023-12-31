package com.koyomiji.rewind.remapper;

import java.util.Arrays;
import java.util.List;
import org.objectweb.asm.Type;

public class TypeHelper {
  public static int sizedIndexToIndex(List<Type> types, int sizedIndex) {
    int sum = 0;

    for (int i = 0; i < types.size(); i++) {
      if (sum == sizedIndex) {
        return i;
      }

      sum += types.get(i).getSize();
    }

    return -1;
  }

  public static int sizedIndexToIndex(Type[] types, int sizedIndex) {
    return sizedIndexToIndex(Arrays.asList(types), sizedIndex);
  }

  public static int lastParamIndex(List<Type> types, boolean isStaticMethod) {
    int sum = 0;

    if (!isStaticMethod) {
      sum += 1;
    }

    for (Type type : types) {
      sum += type.getSize();
    }

    return sum;
  }

  public static int lastParamIndex(Type[] types, boolean isStaticMethod) {
    return lastParamIndex(Arrays.asList(types), isStaticMethod);
  }

  public static Type getClassType(String className) {
    return Type.getType("L" + className + ";");
  }

  public static Type getArrayType(int dimension, Type typeName) {
    StringBuilder brackets = new StringBuilder();

    for (int i = 0; i < dimension; i++) {
      brackets.append("[");
    }

    return Type.getType(brackets + typeName.getDescriptor());
  }

  public static String getInternalClassName(Type type) {
    String desc = type.getDescriptor();
    return desc.substring(1, desc.length() - 1);
  }
}
