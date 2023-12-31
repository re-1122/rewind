package com.koyomiji.rewind.mixin.block;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.block.BlockIce;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockIce.class)
public class MixinBlockIce {
  @ModifyConstant(method = "harvestBlock",
                  constant = @Constant(floatValue = 0.005F))
  private float
  mixin0(float constant) {
    if (ReWindConfig.traditionalExhaustionValue) {
      return 0.025F;
    }

    return constant;
  }
}
