package com.koyomiji.rewind.mixin.enchantment;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentProtection.class)
public class MixinEnchantmentProtection {
  @Shadow @Final public EnchantmentProtection.Type protectionType;

  @Inject(method = "calcModifierDamage", at = @At("HEAD"), cancellable = true)
  private void mixin(int level, DamageSource source,
                     CallbackInfoReturnable<Integer> cir) {
    if (ReWindConfig.oldDamageCalculation) {
      float e = (6 + level * level) / 3.0F;

      if (source.canHarmInCreative()) {
        cir.setReturnValue(0);
      } else if (this.protectionType == EnchantmentProtection.Type.ALL) {
        cir.setReturnValue(MathHelper.floor(e * 0.75F));
      } else if (this.protectionType == EnchantmentProtection.Type.FIRE &&
                 source.isFireDamage()) {
        cir.setReturnValue(MathHelper.floor(e * 1.25F));
      } else if (this.protectionType == EnchantmentProtection.Type.FALL &&
                 source == DamageSource.FALL) {
        cir.setReturnValue(MathHelper.floor(e * 2.5F));
      } else if (this.protectionType == EnchantmentProtection.Type.EXPLOSION &&
                 source.isExplosion()) {
        cir.setReturnValue(MathHelper.floor(e * 1.5F));
      } else if (this.protectionType == EnchantmentProtection.Type.PROJECTILE &&
                 source.isProjectile()) {
        cir.setReturnValue(MathHelper.floor(e * 1.5F));
      } else {
        cir.setReturnValue(0);
      }
    }
  }
}
