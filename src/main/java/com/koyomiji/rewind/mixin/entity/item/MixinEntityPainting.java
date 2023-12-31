package com.koyomiji.rewind.mixin.entity.item;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityPainting.class)
public class MixinEntityPainting {
  @Redirect(
      method = "onBroken",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/item/EntityPainting;playSound(Lnet/minecraft/util/SoundEvent;FF)V")
      )
  private void
  mixin0(EntityPainting instance, SoundEvent soundIn, float volume,
         float pitch) {
    if (ReWindConfig.sounds.noPaintingSound) {
      return;
    }

    instance.playSound(soundIn, volume, pitch);
  }

  @Redirect(
      method = "playPlaceSound",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/item/EntityPainting;playSound(Lnet/minecraft/util/SoundEvent;FF)V")
      )
  private void
  mixin1(EntityPainting instance, SoundEvent soundIn, float volume,
         float pitch) {
    if (ReWindConfig.sounds.noPaintingSound) {
      return;
    }

    instance.playSound(soundIn, volume, pitch);
  }
}
