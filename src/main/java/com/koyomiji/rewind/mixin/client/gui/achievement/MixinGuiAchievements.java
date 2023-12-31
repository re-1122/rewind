package com.koyomiji.rewind.mixin.client.gui.achievement;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.CPacketClientStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiAchievements.class)
public class MixinGuiAchievements extends GuiScreen {
  /**
   * @author Komichi
   * @reason Fix GuiOptionButton invocation
   */
  @Overwrite
  public void initGui() {
    this.mc.getConnection().sendPacket(
        new CPacketClientStatus(CPacketClientStatus.State.REQUEST_STATS));
    this.buttonList.clear();
    this.buttonList.add(new GuiButton(1, this.width / 2 + 24,
                                      this.height / 2 + 74, 80, 20,
                                      I18n.format("gui.done")));
  }
}
