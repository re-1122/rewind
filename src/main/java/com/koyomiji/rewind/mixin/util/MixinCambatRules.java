package com.koyomiji.rewind.mixin.util;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.util.CombatRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CombatRules.class)
public class MixinCambatRules {
  @Inject(method = "getDamageAfterAbsorb", at = @At("HEAD"), cancellable = true)
  private static void getDamageAfterAbsorb(float damage, float totalArmor,
                                           float toughnessAttribute,
                                           CallbackInfoReturnable<Float> cir) {
    if (ReWindConfig.oldDamageCalculation) {
      cir.setReturnValue(damage * (25 - totalArmor) / 25.F);
    }
  }

  @Inject(method = "getDamageAfterMagicAbsorb", at = @At("HEAD"),
          cancellable = true)
  private static void
  getDamageAfterMagicAbsorb(float damage, float enchantModifiers,
                            CallbackInfoReturnable<Float> cir) {
    if (ReWindConfig.oldDamageCalculation) {
      if (enchantModifiers > 20.0F) {
        enchantModifiers = 20.0F;
      }

      cir.setReturnValue(damage * (25 - enchantModifiers) / 25.F);
    }
  }
}
