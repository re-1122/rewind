package com.koyomiji.rewind.mixin.enchantment;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EnumCreatureAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentDamage.class)
public class MixinEnchantmentDamage {
  @Final @Shadow public int damageType;

  @Inject(method = "calcDamageByCreature", at = @At("HEAD"), cancellable = true)
  public void mixin(int level, EnumCreatureAttribute creatureType,
                    CallbackInfoReturnable<Float> cir) {
    if (ReWindConfig.oldDamageCalculation) {
      if (this.damageType == 0) {
        cir.setReturnValue(level * 1.25F);
        return;
      }

      if (this.damageType == 1 &&
          creatureType == EnumCreatureAttribute.UNDEAD) {
        cir.setReturnValue(level * 2.5F);
        return;
      }

      if (this.damageType == 2 &&
          creatureType == EnumCreatureAttribute.ARTHROPOD) {
        cir.setReturnValue(level * 2.5F);
        return;
      }

      cir.setReturnValue(0.0F);
    }
  }
}
