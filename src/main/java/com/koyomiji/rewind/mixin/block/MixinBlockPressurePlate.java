package com.koyomiji.rewind.mixin.block;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.block.BlockPressurePlate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockPressurePlate.class)
public class MixinBlockPressurePlate {
  @ModifyConstant(method = "playClickOnSound",
                  constant = @Constant(floatValue = 0.8F))
  private float
  mixin0(float original) {
    if (ReWindConfig.sounds.traditionalSwitchSound) {
      return 0.6F;
    }

    return original;
  }

  @ModifyConstant(method = "playClickOffSound",
                  constant = @Constant(floatValue = 0.7F))
  private float
  mixin1(float original) {
    if (ReWindConfig.sounds.traditionalSwitchSound) {
      return 0.5F;
    }

    return original;
  }
}
