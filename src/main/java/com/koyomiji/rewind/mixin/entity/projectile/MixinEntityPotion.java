package com.koyomiji.rewind.mixin.entity.projectile;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityPotion.class)
public class MixinEntityPotion {
  @Redirect(
      method = "applySplash",
      at = @At(value = "INVOKE",
               target = "Lnet/minecraft/potion/PotionEffect;getDuration()I"))
  private int
  mixin(PotionEffect instance) {
    if (ReWindConfig.traditionalPotionEffects) {
      return (int)(instance.getDuration() * (3. / 4));
    }

    return instance.getDuration();
  }
}
