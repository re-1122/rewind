package com.koyomiji.rewind.debug;

public abstract class AbstractDebugProfile implements IDebugProfile {
  protected static long bytesToMBytes(long bytes) {
    return bytes / 1024L / 1024L;
  }
}
