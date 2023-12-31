package com.koyomiji.rewind.stats;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketStatistics;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;

public interface IStatisticsManagerServerExt {

  void sendAchievements(EntityPlayerMP player);

  boolean hasUnsentAchievement();
}
