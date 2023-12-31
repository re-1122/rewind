package com.koyomiji.rewind.remapper;

import org.objectweb.asm.commons.Remapper;

public class DirectRemapper extends Remapper {
  private final Mapping mapping;

  public DirectRemapper(Mapping mapping) { this.mapping = mapping; }

  @Override
  public String map(String typeName) {
    String remapped = mapping.mapClassName(typeName);
    return ObjectHelper.orDefault(remapped, typeName);
  }

  @Override
  public String mapMethodName(String owner, String name, String desc) {
    String remapped = mapping.mapMethodName(owner, name, desc);
    return ObjectHelper.orDefault(remapped, name);
  }

  @Override
  public String mapFieldName(String owner, String name, String desc) {
    String remapped = mapping.mapFieldName(owner, name);
    return ObjectHelper.orDefault(remapped, name);
  }
}
