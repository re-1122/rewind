package com.koyomiji.rewind.mixin.block;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.block.BlockPressurePlateWeighted;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockPressurePlateWeighted.class)
public class MixinBlockPressurePlateWeighted {
  @ModifyConstant(method = "playClickOnSound",
                  constant = @Constant(floatValue = 0.90000004F))
  private float
  mixin0(float original) {
    if (ReWindConfig.sounds.traditionalSwitchSound) {
      return 0.6F;
    }

    return original;
  }

  @ModifyConstant(method = "playClickOffSound",
                  constant = @Constant(floatValue = 0.75F))
  private float
  mixin1(float original) {
    if (ReWindConfig.sounds.traditionalSwitchSound) {
      return 0.5F;
    }

    return original;
  }
}
