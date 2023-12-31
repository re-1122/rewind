package com.koyomiji.rewind;

import java.util.Objects;

public class ChunkLocation {
  public int x;
  public int z;

  public ChunkLocation(int x, int z) {
    this.x = x;
    this.z = z;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ChunkLocation that = (ChunkLocation)o;
    return x == that.x && z == that.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, z);
  }
}
