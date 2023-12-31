package com.koyomiji.rewind.stats;

import net.minecraft.stats.StatBase;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public interface IStatBaseExt {
  StatBase setSerializableClazz(Class<? extends IJsonSerializable> clazz);

  ITextComponent createChatComponent();

  boolean isAchievement();
}
