package com.koyomiji.rewind.mixin.client.gui;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends Gui {
  @Redirect(
      method = "drawScreen(IIF)V", require = 1,
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
          ordinal = 1))
  private void
  mixin(TextureManager self, ResourceLocation resource) {
    if (ReWindConfig.hideJavaEdition) {
      return;
    }

    self.bindTexture(resource);
  }

  @Redirect(
      method = "drawScreen(IIF)V", require = 1,
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/gui/GuiMainMenu;drawModalRectWithCustomSizedTexture(IIFFIIFF)V",
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