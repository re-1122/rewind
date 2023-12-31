package com.koyomiji.rewind.mixin.entity;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityLeashKnot.class)
public class MixinEntityLeashKnot {
  @Redirect(
      method = "onBroken",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/EntityLeashKnot;playSound(Lnet/minecraft/util/SoundEvent;FF)V")
      )
  private void
  mixin0(EntityLeashKnot instance, SoundEvent soundIn, float volume,
         float pitch) {
    if (ReWindConfig.sounds.noLeashKnotSound) {
      return;
    }

    instance.playSound(soundIn, volume, pitch);
  }

  @Redirect(
      method = "playPlaceSound",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/EntityLeashKnot;playSound(Lnet/minecraft/util/SoundEvent;FF)V")
      )
  private void
  mixin1(EntityLeashKnot instance, SoundEvent soundIn, float volume,
         float pitch) {
    if (ReWindConfig.sounds.noLeashKnotSound) {
      return;
    }

    instance.playSound(soundIn, volume, pitch);
  }
}
