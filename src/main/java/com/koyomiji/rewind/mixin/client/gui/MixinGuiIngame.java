package com.koyomiji.rewind.mixin.client.gui;

import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.debug.DebugOverlayStyle;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {
  @Redirect(
      method =
          "renderAttackIndicator(FLnet/minecraft/client/gui/ScaledResolution;)V",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/client/settings/GameSettings;showDebugInfo:Z"))
  private boolean
  mixin(GameSettings self) {
    if (ReWindConfig.debugOverlayStyle == DebugOverlayStyle.VANILLA_1_7) {
      return false;
    }

    return self.showDebugInfo;
  }

  @Redirect(
      method =
          "renderAttackIndicator(FLnet/minecraft/client/gui/ScaledResolution;)V",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/client/settings/GameSettings;thirdPersonView:I"))
  private int
  mixin3(GameSettings self) {
    if (ReWindConfig.showCrosshairInThirdPersonView) {
      return 0;
    }

    return self.thirdPersonView;
  }

  @Redirect(
      method =
          "renderAttackIndicator(FLnet/minecraft/client/gui/ScaledResolution;)V",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/client/settings/GameSettings;attackIndicator:I"))
  private int
  mixin2(GameSettings self) {
    if (ReWindConfig.oldCombat) {
      return 0;
    }

    return self.attackIndicator;
  }

  @Inject(method = "renderPotionEffects", at = @At("HEAD"), cancellable = true)
  private void mixin4(ScaledResolution resolution, CallbackInfo ci) {
    if (ReWindConfig.hideActiveEffects) {
      ci.cancel();
    }
  }
}
