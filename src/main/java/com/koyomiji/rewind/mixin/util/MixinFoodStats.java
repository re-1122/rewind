package com.koyomiji.rewind.mixin.util;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.EnumDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(FoodStats.class)
public class MixinFoodStats {
  @Shadow private float foodSaturationLevel;

  @Redirect(
      method = "onUpdate",
      at = @At(value = "FIELD",
               target = "Lnet/minecraft/util/FoodStats;foodSaturationLevel:F",
               ordinal = 3))
  private float
  mixin(FoodStats foodStats) {
    if (ReWindConfig.disableFasterRegeneration) {
      return 0.0F;
    }

    return foodSaturationLevel;
  }

  @ModifyConstant(method = "onUpdate",
                  constant = @Constant(floatValue = 6.0F, ordinal = 2))
  private float
  mixin2(float constant) {
    if (ReWindConfig.traditionalExhaustionValue) {
      return 3.0F;
    }

    return constant;
  }
}
