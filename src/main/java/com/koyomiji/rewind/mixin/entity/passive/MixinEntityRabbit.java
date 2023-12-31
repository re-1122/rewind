package com.koyomiji.rewind.mixin.entity.passive;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRabbit.class)
public class MixinEntityRabbit {
  @Redirect(
      method = "attackEntityAsMob(Lnet/minecraft/entity/Entity;)Z",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/passive/EntityRabbit;playSound(Lnet/minecraft/util/SoundEvent;FF)V")
      )
  private void
  mixin(EntityRabbit instance, SoundEvent soundIn, float volume, float pitch) {
    if (ReWindConfig.sounds.noKillerBunnyAttackSound) {
      return;
    }

    instance.playSound(soundIn, volume, pitch);
  }
}
