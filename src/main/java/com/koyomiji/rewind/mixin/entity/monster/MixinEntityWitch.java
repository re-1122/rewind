package com.koyomiji.rewind.mixin.entity.monster;

import com.koyomiji.rewind.config.ReWindConfig;
import javax.annotation.Nullable;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityWitch.class)
public class MixinEntityWitch {
  @Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
  protected void mixin0(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.noWitchSound) {
      cir.setReturnValue(null);
    }
  }

  @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
  protected void mixin1(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.noWitchSound) {
      cir.setReturnValue(null);
    }
  }

  @Inject(method = "getDeathSound", at = @At("HEAD"), cancellable = true)
  protected void mixin2(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.noWitchSound) {
      cir.setReturnValue(null);
    }
  }

  @Redirect(
      method = "onLivingUpdate()V",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/EntityPlayer;DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V")
      )
  private void
  mixin(World world, @Nullable EntityPlayer player, double x, double y,
        double z, SoundEvent soundIn, SoundCategory category, float volume,
        float pitch) {
    if (ReWindConfig.sounds.noWitchSound) {
      return;
    }

    world.playSound(player, x, y, z, soundIn, category, volume, pitch);
  }

  @Redirect(
      method =
          "attackEntityWithRangedAttack(Lnet/minecraft/entity/EntityLivingBase;F)V",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/EntityPlayer;DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V")
      )
  private void
  mixin2(World world, @Nullable EntityPlayer player, double x, double y,
         double z, SoundEvent soundIn, SoundCategory category, float volume,
         float pitch) {
    if (ReWindConfig.sounds.noWitchSound) {
      return;
    }

    world.playSound(player, x, y, z, soundIn, category, volume, pitch);
  }
}
