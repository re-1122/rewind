package com.koyomiji.rewind.mixin.stats;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Achievement.class)
public abstract class MixinAchievement extends StatBase {
  @Shadow(remap = false) public abstract boolean getSpecial();

  @Shadow(remap = false) public abstract String getDescription();

  public MixinAchievement(String statIdIn, ITextComponent statNameIn,
                          IStatType formatterIn) {
    super(statIdIn, statNameIn, formatterIn);
  }

  /**
   * @author Komichi
   * @reason Add hover text to achievements
   */
  @Overwrite
  public ITextComponent getStatName() {
    ITextComponent name = super.getStatName();
    name.getStyle().setColor(getSpecial() ? TextFormatting.DARK_PURPLE
                                          : TextFormatting.GREEN);

    ITextComponent hoverText = new TextComponentString("");
    hoverText.appendSibling(name.createCopy());
    hoverText.appendText("\n");
    hoverText.appendText(getDescription());
    name.getStyle().setHoverEvent(
        new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
    return name;
  }
}
