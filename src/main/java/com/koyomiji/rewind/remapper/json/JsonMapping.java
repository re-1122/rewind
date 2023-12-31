package com.koyomiji.rewind.remapper.json;

import com.koyomiji.rewind.remapper.Mapping;
import com.koyomiji.rewind.remapper.MemberIdentifier;
import java.util.HashMap;
import java.util.Map;

public class JsonMapping {
  public Map<String, String> classes;
  public Map<String, String> methods;
  public Map<String, String> fields;
  public Map<String, String[]> paramNames;

  public JsonMapping() {
    classes = new HashMap<>();
    methods = new HashMap<>();
    fields = new HashMap<>();
    paramNames = new HashMap<>();
  }

  public static JsonMapping from(Mapping mapping) {
    JsonMapping jsonMapping = new JsonMapping();
    jsonMapping.classes = mapping.classes;
    jsonMapping.methods = new HashMap<>();
    jsonMapping.fields = new HashMap<>();
    jsonMapping.paramNames = new HashMap<>();

    for (Map.Entry<MemberIdentifier, String> entry :
         mapping.methods.entrySet()) {
      jsonMapping.methods.put(entry.getKey().toString(), entry.getValue());
    }

    for (Map.Entry<MemberIdentifier, String> entry :
         mapping.fields.entrySet()) {
      jsonMapping.fields.put(entry.getKey().toString(), entry.getValue());
    }

    for (Map.Entry<MemberIdentifier, String[]> entry :
         mapping.paramNames.entrySet()) {
      jsonMapping.paramNames.put(entry.getKey().toString(), entry.getValue());
    }

    return jsonMapping;
  }

  public Mapping to() {
    Mapping mapping = new Mapping();
    mapping.classes = classes;
    mapping.methods = new HashMap<>();
    mapping.fields = new HashMap<>();
    mapping.paramNames = new HashMap<>();

    for (Map.Entry<String, String> entry : methods.entrySet()) {
      mapping.methods.put(MemberIdentifier.fromString(entry.getKey()),
                          entry.getValue());
    }

    for (Map.Entry<String, String> entry : fields.entrySet()) {
      mapping.fields.put(MemberIdentifier.fromString(entry.getKey()),
                         entry.getValue());
    }

    for (Map.Entry<String, String[]> entry : paramNames.entrySet()) {
      mapping.paramNames.put(MemberIdentifier.fromString(entry.getKey()),
                             entry.getValue());
    }

    return mapping;
  }
}
