package com.koyomiji.rewind.mixin.tileentity;

import com.koyomiji.rewind.SoundRegistry;
import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TileEntityChest.class)
public class MixinTileEntityChest {
  @Redirect(
      method = "update",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_CHEST_OPEN:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin0() {
    if (ReWindConfig.sounds.traditionalChestSound) {
      return SoundRegistry.BLOCK_CHEST_OPEN;
    }

    return SoundEvents.BLOCK_CHEST_OPEN;
  }

  @Redirect(
      method = "update",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_CHEST_CLOSE:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin1() {
    if (ReWindConfig.sounds.traditionalChestSound) {
      return SoundRegistry.BLOCK_CHEST_CLOSE;
    }

    return SoundEvents.BLOCK_CHEST_CLOSE;
  }
}
