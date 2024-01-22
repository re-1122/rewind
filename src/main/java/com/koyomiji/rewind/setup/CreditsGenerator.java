package com.koyomiji.rewind.setup;

import com.koyomiji.refound.asset.AssetIdentifier;
import java.util.*;

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

    List<Map.Entry<AssetIdentifier, Set<String>>> entries =
        new ArrayList<>(assets.entrySet());
    entries.sort(Comparator.comparing(e -> e.getKey().url.toString()));

    for (Map.Entry<AssetIdentifier, Set<String>> e : assets.entrySet()) {
      sb.append("From ").append(e.getKey().url).append(":\n");

      List<String> paths = new ArrayList<>(e.getValue());
      paths.sort(Comparator.naturalOrder());

      for (String path : paths) {
        sb.append("  ").append(path).append("\n");
      }
    }

    return sb.toString();
  }
}
