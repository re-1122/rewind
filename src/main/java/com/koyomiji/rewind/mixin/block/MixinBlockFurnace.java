package com.koyomiji.rewind.mixin.block;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.block.BlockFurnace;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockFurnace.class)
public class MixinBlockFurnace {
  @Redirect(
      method = "randomDisplayTick",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/World;playSound(DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FFZ)V")
      )
  private void
  mixin0(World instance, double x, double y, double z, SoundEvent soundIn,
         SoundCategory category, float volume, float pitch,
         boolean distanceDelay) {
    if (ReWindConfig.sounds.noFurnaceSound) {
      return;
    }

    instance.playSound(x, y, z, soundIn, category, volume, pitch,
                       distanceDelay);
  }
}
