package com.koyomiji.rewind.mixin.block;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.block.BlockButtonWood;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockButtonWood.class)
public class MixinBlockButtonWood {
  @Redirect(
      method = "playClickSound",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_WOOD_BUTTON_CLICK_ON:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin0() {
    if (ReWindConfig.sounds.traditionalSwitchSound) {
      return SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON;
    }

    return SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON;
  }

  @Redirect(
      method = "playReleaseSound",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_WOOD_BUTTON_CLICK_OFF:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin1() {
    if (ReWindConfig.sounds.traditionalSwitchSound) {
      return SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
    }

    return SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF;
  }
}
