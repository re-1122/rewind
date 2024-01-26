package com.koyomiji.rewind.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.util.registry.RegistryNamespaced;

public class FilteredRegistryNamaspaced<K, V> extends RegistryNamespaced<K, V> {
  RegistryNamespaced<K, V> registry;
  Predicate<V> filter;
  Map<K, V> filteredMap = new HashMap<>();

  public FilteredRegistryNamaspaced(RegistryNamespaced<K, V> registry,
                                    Predicate<V> filter) {
    this.registry = registry;
    this.filter = filter;

    for (K key : registry.getKeys()) {
      V value = registry.getObject(key);

      if (filter.test(value)) {
        filteredMap.put(key, value);
      }
    }
  }

  @Override
  public V getRandomObject(Random random) {
    V object = registry.getRandomObject(random);

    while (!filter.test(object)) {
      object = registry.getRandomObject(random);
    }

    return object;
  }

  public Iterator<V> iterator() { return filteredMap.values().iterator(); }
}
