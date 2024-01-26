package com.koyomiji.rewind.mixin.client.gui;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiWinGame.class)
public class MixinGuiWinGame extends GuiScreen {
  @Redirect(
      method = "drawScreen",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
          ordinal = 1))
  private void
  mixin(TextureManager instance, ResourceLocation resource) {
    if (ReWindConfig.hideJavaEdition) {
      return;
    }

    instance.bindTexture(resource);
  }

  @Redirect(
      method = "drawScreen",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/gui/GuiWinGame;drawModalRectWithCustomSizedTexture(IIFFIIFF)V",
          ordinal = 0))
  private void
  mixin2(int x, int y, float u, float v, int width, int height,
         float textureWidth, float textureHeight) {
    if (ReWindConfig.hideJavaEdition) {
      return;
    }

    drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth,
                                        textureHeight);
  }
}
