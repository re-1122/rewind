package net.minecraft.client.gui.achievement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;

/*
 * This is a skeleton file representing 1.11.2's
 * net/minecraft/client/gui/achievement/GuiAchievement. Actual implementation
 * will be obtained at runtime.
 */
public class GuiAchievement extends Gui {
  private static final ResourceLocation ACHIEVEMENT_BG = null;
  private final Minecraft mc = null;
  private int width;
  private int height;
  private String achievementTitle;
  private String achievementDescription;
  private Achievement achievement;
  private long notificationTime;
  private final RenderItem renderItem = null;
  private boolean permanentNotification;

  public GuiAchievement(Minecraft mc) {}

  public void displayAchievement(Achievement ach) {}

  public void displayUnformattedAchievement(Achievement achievementIn) {}

  private void updateAchievementWindowScale() {}

  public void updateAchievementWindow() {}

  public void clearAchievements() {}
}
