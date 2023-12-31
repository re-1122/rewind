package com.koyomiji.rewind.debug;

public enum DebugOverlayStyle {
  DEFAULT,
  VANILLA_1_7,
  VANILLA_1_8,
  VANILLA_1_12;

  public String toString() { return this.name().toLowerCase(); }
}