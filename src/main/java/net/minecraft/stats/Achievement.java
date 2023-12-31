package net.minecraft.stats;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.text.ITextComponent;

/*
 * This is a skeleton file representing 1.11.2's
 * net/minecraft/stats/Achievement. Actual implementation will be obtained at
 * runtime.
 */
public class Achievement extends StatBase {
  public final int displayColumn = 0;
  public final int displayRow = 0;
  public final Achievement parentAchievement = null;
  private final String achievementDescription = null;
  private IStatStringFormat statStringFormatter;
  public final ItemStack icon = null;
  private boolean isSpecial;

  public Achievement(String statIdIn, String unlocalizedName, int column,
                     int row, Item itemIn, Achievement parent) {
    super(null, null);
  }

  public Achievement(String statIdIn, String unlocalizedName, int column,
                     int row, Block blockIn, Achievement parent) {
    super(null, null);
  }

  public Achievement(String statIdIn, String unlocalizedName, int column,
                     int row, ItemStack stack, Achievement parent) {
    super(null, null);
  }

  public Achievement initIndependentStat() { return null; }

  public Achievement setSpecial() { return null; }

  public Achievement registerStat() { return null; }

  public boolean isAchievement() { return true; }

  public ITextComponent getStatName() { return null; }

  public Achievement
  setSerializableClazz(Class<? extends IJsonSerializable> clazz) {
    return null;
  }

  public String getDescription() { return null; }

  public Achievement
  setStatStringFormatter(IStatStringFormat statStringFormatterIn) {
    return null;
  }

  public boolean getSpecial() { return false; }
}
