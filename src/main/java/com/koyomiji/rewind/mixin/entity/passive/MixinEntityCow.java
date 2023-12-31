package com.koyomiji.rewind.mixin.entity.passive;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityCow.class)
public class MixinEntityCow {
  @Redirect(
      method = "processInteract",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/player/EntityPlayer;playSound(Lnet/minecraft/util/SoundEvent;FF)V")
      )
  private void
  mixin0(EntityPlayer instance, SoundEvent soundIn, float volume, float pitch) {
    if (ReWindConfig.sounds.noCowMilkSound) {
      return;
    }

    instance.playSound(soundIn, volume, pitch);
  }
}
