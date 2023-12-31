package net.minecraft.client.gui.achievement;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.ResourceLocation;

/*
 * This is a skeleton file representing 1.11.2's
 * net/minecraft/client/gui/achievement/GuiAchievements. Actual implementation
 * will be obtained at runtime.
 *
 * doneLoading -> onStatsUpdated
 */
public class GuiAchievements extends GuiScreen implements IProgressMeter {
  private static final int X_MIN = 0;
  private static final int Y_MIN = 0;
  private static final int X_MAX = 0;
  private static final int Y_MAX = 0;
  private static final ResourceLocation ACHIEVEMENT_BACKGROUND = null;
  protected GuiScreen parentScreen;
  protected int imageWidth;
  protected int imageHeight;
  protected int xLastScroll;
  protected int yLastScroll;
  protected float zoom;
  protected double xScrollO;
  protected double yScrollO;
  protected double xScrollP;
  protected double yScrollP;
  protected double xScrollTarget;
  protected double yScrollTarget;
  private int scrolling;
  private final StatisticsManager statFileWriter = null;
  private boolean loadingAchievements;

  public GuiAchievements(GuiScreen parentScreenIn,
                         StatisticsManager statFileWriterIn) {}

  public void initGui() {}

  protected void actionPerformed(GuiButton var1) {}

  protected void keyTyped(char var1, int keyCode) {}

  public void drawScreen(int mouseX, int mouseY, float partialTicks) {}

  public void onStatsUpdated() {}

  public void updateScreen() {}

  protected void drawTitle() {}

  protected void drawAchievementScreen(int p_146552_1_, int p_146552_2_,
                                       float p_146552_3_) {}

  private TextureAtlasSprite getTexture(Block blockIn) { return null; }

  public boolean doesGuiPauseGame() { return false; }

  static {}
}
