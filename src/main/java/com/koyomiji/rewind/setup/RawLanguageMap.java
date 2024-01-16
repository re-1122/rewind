package com.koyomiji.rewind.setup;

import java.util.HashMap;
import java.util.Map;

public class RawLanguageMap {
  public static Map<String, String> parse(String string) {
    Map<String, String> map = new HashMap<>();

    String[] lines = string.split("\n");

    for (String line : lines) {
      if (line.startsWith("#")) {
        continue;
      }

      String[] split = line.split("=");

      if (split.length != 2) {
        continue;
      }

      map.put(split[0], split[1]);
    }

    return map;
  }

  public static String stringify(Map<String, String> map) {
    StringBuilder sb = new StringBuilder();

    for (Map.Entry<String, String> entry : map.entrySet()) {
      sb.append(entry.getKey())
          .append("=")
          .append(entry.getValue())
          .append("\n");
    }

    return sb.toString();
  }
}
