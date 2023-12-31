package com.koyomiji.rewind.world.gen.feature;

import net.minecraft.world.gen.feature.WorldGenSpikes;

public class OldEndSpike extends WorldGenSpikes.EndSpike {
  /*
   * Height of the obsidian pillar
   */
  private final int actualHeight;
  /*
   * Height of the pillar base
   * actualHeight + baseHeight = EndSpike#height
   */
  private final int baseHeight;

  public OldEndSpike(int centerX, int centerZ, int radius, int actualHeight,
                     int baseHeight) {
    super(centerX, centerZ, radius, actualHeight + baseHeight, false);
    this.actualHeight = actualHeight;
    this.baseHeight = baseHeight;
  }

  public int getActualHeight() { return actualHeight; }

  public int getBaseHeight() { return baseHeight; }
}
