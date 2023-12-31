package com.koyomiji.rewind.mixin.potion;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Potion.class)
public class MixinPotion {
  @ModifyConstant(method = "registerPotions",
                  constant = @Constant(doubleValue = -4.0D))
  private static double
  mixin(double value) {
    return ReWindConfig.traditionalPotionEffects ? -0.5D : value;
  }

  @ModifyConstant(method = "registerPotions",
                  constant = @Constant(doubleValue = 3.0D))
  private static double
  mixin2(double value) {
    return ReWindConfig.traditionalPotionEffects ? 1.3D : value;
  }

  @ModifyConstant(method = "registerPotions",
                  constant = @Constant(intValue = 0, ordinal = 9))
  private static int
  mixin3(int value) {
    return ReWindConfig.traditionalPotionEffects ? 2 : value;
  }

  @ModifyConstant(method = "performEffect",
                  constant = @Constant(floatValue = 0.005F))
  private float
  mixin4(float constant) {
    if (ReWindConfig.traditionalExhaustionValue) {
      return 0.025F;
    }

    return constant;
  }
}
