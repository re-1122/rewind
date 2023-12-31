package com.koyomiji.rewind.mixin.client.gui;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiGameOver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiGameOver.class)
public class MixinGuiGameOver {
  @Redirect(
      method = "drawScreen(IIF)V",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/gui/GuiGameOver;drawCenteredString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V",
          ordinal = 1))
  private void
  mixin(GuiGameOver gui, FontRenderer fontRendererIn, String text, int x, int y,
        int color) {
    if (ReWindConfig.hideDeathCauseOnGameOverScreen) {
      return;
    }

    gui.drawCenteredString(fontRendererIn, text, x, y, color);
  }
}
