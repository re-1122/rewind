package com.koyomiji.rewind.mixin.potion;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.potion.PotionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PotionType.class)
public class MixinPotionType {
  @ModifyConstant(method = "registerPotionTypes",
                  constant = @Constant(intValue = 1800, ordinal = 4))
  private static int
  mixin0(int value) {
    if (ReWindConfig.traditionalPotionEffects) {
      return 2400;
    }

    return value;
  }

  @ModifyConstant(method = "registerPotionTypes",
                  constant = @Constant(intValue = 1800, ordinal = 3))
  private static int
  mixin1(int value) {
    if (ReWindConfig.traditionalPotionEffects) {
      return 2400;
    }

    return value;
  }

  @ModifyConstant(method = "registerPotionTypes",
                  constant = @Constant(intValue = 432))
  private static int
  mixin2(int value) {
    if (ReWindConfig.traditionalPotionEffects) {
      return 450;
    }

    return value;
  }
}
