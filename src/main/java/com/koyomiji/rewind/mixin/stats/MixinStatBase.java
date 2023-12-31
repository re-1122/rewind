package com.koyomiji.rewind.mixin.stats;

import com.koyomiji.rewind.stats.IStatBaseExt;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StatBase.class)
public abstract class MixinStatBase implements IStatBaseExt {
  @Shadow private Class<? extends IJsonSerializable> serializableClazz;

  @Shadow public abstract ITextComponent getStatName();

  public StatBase
  setSerializableClazz(Class<? extends IJsonSerializable> clazz) {
    this.serializableClazz = clazz;
    return (StatBase)(Object)this;
  }

  public ITextComponent createChatComponent() {
    ITextComponent name = getStatName();
    ITextComponent bracketed =
        (new TextComponentString("[")).appendSibling(name).appendText("]");
    bracketed.setStyle(name.getStyle());
    return bracketed;
  }

  public boolean isAchievement() { return false; }
}
