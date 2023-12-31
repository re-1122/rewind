package com.koyomiji.rewind.mixin.stats;

import com.koyomiji.rewind.stats.IStatBaseExt;
import com.koyomiji.rewind.stats.IStatisticsManagerExt;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StatisticsManager.class)
public abstract class MixinStatisticsManager implements IStatisticsManagerExt {
  @Shadow public abstract int readStat(StatBase stat);

  @Shadow
  public abstract void unlockAchievement(EntityPlayer playerIn, StatBase statIn,
                                         int p_150873_3_);

  @Shadow @Final protected Map<StatBase, TupleIntJsonSerializable> statsData;

  /**
   * @author Komichi
   * @reason Add achievement test
   */
  @Overwrite
  public void increaseStat(EntityPlayer player, StatBase stat, int amount) {
    if (!((IStatBaseExt)stat).isAchievement() ||
        canUnlockAchievement((Achievement)stat)) {
      this.unlockAchievement(player, stat, this.readStat(stat) + amount);
    }
  }

  public boolean hasAchievementUnlocked(Achievement achievement) {
    return this.readStat(achievement) > 0;
  }

  public boolean canUnlockAchievement(Achievement achievement) {
    return achievement.parentAchievement == null ||
        hasAchievementUnlocked(achievement.parentAchievement);
  }

  public int countRequirementsUntilAvailable(Achievement achievement) {
    if (hasAchievementUnlocked(achievement)) {
      return 0;
    } else {
      int count = 0;

      for (Achievement a = achievement.parentAchievement;
           a != null && !hasAchievementUnlocked(a); count++) {
        a = a.parentAchievement;
      }

      return count;
    }
  }

  public <T extends IJsonSerializable> T getProgress(StatBase p_150870_1_) {
    TupleIntJsonSerializable tuple =
        (TupleIntJsonSerializable)this.statsData.get(p_150870_1_);
    return tuple == null ? null : (T)tuple.getJsonSerializableValue();
  }

  public <T extends IJsonSerializable> T setProgress(StatBase p_150872_1_,
                                                     T p_150872_2_) {
    TupleIntJsonSerializable tuple =
        (TupleIntJsonSerializable)this.statsData.get(p_150872_1_);

    if (tuple == null) {
      tuple = new TupleIntJsonSerializable();
      this.statsData.put(p_150872_1_, tuple);
    }

    tuple.setJsonSerializableValue(p_150872_2_);
    return (T)p_150872_2_;
  }
}
