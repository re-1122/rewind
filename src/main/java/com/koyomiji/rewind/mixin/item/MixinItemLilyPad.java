package com.koyomiji.rewind.mixin.item;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemLilyPad;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemLilyPad.class)
public class MixinItemLilyPad {
  @Redirect(
      method = "onItemRightClick",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V")
      )
  private void
  mixin0(World instance, EntityPlayer player, BlockPos pos, SoundEvent soundIn,
         SoundCategory category, float volume, float pitch) {
    if (ReWindConfig.sounds.noLilyPadSound) {
      return;
    }

    instance.playSound(player, pos, soundIn, category, volume, pitch);
  }
}
