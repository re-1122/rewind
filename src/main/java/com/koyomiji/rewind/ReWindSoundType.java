package com.koyomiji.rewind;

import net.minecraft.block.SoundType;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class ReWindSoundType {
  public static SoundType GLASS = new SoundType(
      1.0F, 1.0F, SoundEvents.BLOCK_GLASS_BREAK, SoundEvents.BLOCK_GLASS_STEP,
      null, SoundEvents.BLOCK_GLASS_HIT, SoundEvents.BLOCK_GLASS_FALL) {
    @Override
    public SoundEvent getPlaceSound() {
      return ReWindSoundEvents.BLOCK_GLASS_PLACE;
    }
  };
}
