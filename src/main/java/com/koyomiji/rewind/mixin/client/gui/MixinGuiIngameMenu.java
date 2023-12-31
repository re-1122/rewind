package com.koyomiji.rewind.mixin.client.gui;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiAchievements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiIngameMenu.class)
public class MixinGuiIngameMenu extends GuiScreen {
  @ModifyConstant(method = "initGui",
                  constant = @Constant(stringValue = "gui.advancements"))
  private String
  mixin2(String string) {
    if (ReWindConfig.enableAchievement) {
      return "gui.achievements";
    }

    return string;
  }

  @Redirect(
      method = "actionPerformed",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V",
          ordinal = 4))
  private void
  mixin(Minecraft instance, GuiScreen screen) {
    if (ReWindConfig.enableAchievement) {
      instance.displayGuiScreen(
          new GuiAchievements(this, mc.player.getStatFileWriter()));
      return;
    }

    instance.displayGuiScreen(screen);
  }
}
