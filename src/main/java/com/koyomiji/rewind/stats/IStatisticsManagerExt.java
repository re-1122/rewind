package com.koyomiji.rewind.stats;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;

public interface IStatisticsManagerExt {
  boolean hasAchievementUnlocked(Achievement achievement);

  boolean canUnlockAchievement(Achievement achievement);

  int countRequirementsUntilAvailable(Achievement achievement);

  <T extends IJsonSerializable> T getProgress(StatBase p_150870_1_);

  <T extends IJsonSerializable> T setProgress(StatBase p_150872_1_,
                                              T p_150872_2_);
}
