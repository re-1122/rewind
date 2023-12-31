package com.koyomiji.rewind;

import com.koyomiji.refound.asset.AssetIdentifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CreditsGenerator {
  public Map<AssetIdentifier, Set<String>> assets = new HashMap<>();

  public CreditsGenerator() {}

  public void add(AssetIdentifier ai, String path) {
    if (!assets.containsKey(ai)) {
      assets.put(ai, new HashSet<>());
    }

    assets.get(ai).add(path);
  }

  public String generate() {
    StringBuilder sb = new StringBuilder();
    sb.append(
        "The following files are not part of the distributed jar file and were obtained at runtime.\n");

    for (Map.Entry<AssetIdentifier, Set<String>> e : assets.entrySet()) {
      sb.append("From ").append(e.getKey().url).append(":\n");

      for (String path : e.getValue()) {
        sb.append("  ").append(path).append("\n");
      }
    }

    return sb.toString();
  }
}
