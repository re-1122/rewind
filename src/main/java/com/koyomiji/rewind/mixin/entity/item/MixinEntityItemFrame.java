package com.koyomiji.rewind.mixin.entity.item;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityItemFrame.class)
public class MixinEntityItemFrame {
  @Redirect(
      method = "attackEntityFrom",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/item/EntityItemFrame;playSound(Lnet/minecraft/util/SoundEvent;FF)V")
      )
  private void
  mixin0(EntityItemFrame instance, SoundEvent soundIn, float volume,
         float pitch) {
    if (ReWindConfig.sounds.noItemFrameSound) {
      return;
    }

    instance.playSound(soundIn, volume, pitch);
  }

  @Redirect(
      method = "onBroken",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/item/EntityItemFrame;playSound(Lnet/minecraft/util/SoundEvent;FF)V")
      )
  private void
  mixin1(EntityItemFrame instance, SoundEvent soundIn, float volume,
         float pitch) {
    if (ReWindConfig.sounds.noItemFrameSound) {
      return;
    }

    instance.playSound(soundIn, volume, pitch);
  }

  @Redirect(
      method = "playPlaceSound",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/item/EntityItemFrame;playSound(Lnet/minecraft/util/SoundEvent;FF)V")
      )
  private void
  mixin2(EntityItemFrame instance, SoundEvent soundIn, float volume,
         float pitch) {
    if (ReWindConfig.sounds.noItemFrameSound) {
      return;
    }

    instance.playSound(soundIn, volume, pitch);
  }

  @Redirect(
      method = "setDisplayedItemWithUpdate",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/item/EntityItemFrame;playSound(Lnet/minecraft/util/SoundEvent;FF)V")
      )
  private void
  mixin3(EntityItemFrame instance, SoundEvent soundIn, float volume,
         float pitch) {
    if (ReWindConfig.sounds.noItemFrameSound) {
      return;
    }

    instance.playSound(soundIn, volume, pitch);
  }

  @Redirect(
      method = "processInitialInteract",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/item/EntityItemFrame;playSound(Lnet/minecraft/util/SoundEvent;FF)V")
      )
  private void
  mixin4(EntityItemFrame instance, SoundEvent soundIn, float volume,
         float pitch) {
    if (ReWindConfig.sounds.noItemFrameSound) {
      return;
    }

    instance.playSound(soundIn, volume, pitch);
  }
}
