package com.koyomiji.rewind.mixin.item;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemGlassBottle.class)
public class MixinItemGlassBottle {
  @Redirect(
      method = "onItemRightClick",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/EntityPlayer;DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V")
      )
  private void
  mixin0(World instance, EntityPlayer player, double x, double y, double z,
         SoundEvent soundIn, SoundCategory category, float volume,
         float pitch) {
    if (ReWindConfig.sounds.noGlassBottleSound) {
      return;
    }

    instance.playSound(player, x, y, z, soundIn, category, volume, pitch);
  }
}
