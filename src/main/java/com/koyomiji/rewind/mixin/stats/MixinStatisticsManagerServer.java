package com.koyomiji.rewind.mixin.stats;

import com.google.common.collect.Maps;
import com.koyomiji.rewind.ReWind;
import com.koyomiji.rewind.server.IMinecraftServerExt;
import com.koyomiji.rewind.stats.IStatBaseExt;
import com.koyomiji.rewind.stats.IStatisticsManagerExt;
import com.koyomiji.rewind.stats.IStatisticsManagerServerExt;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketStatistics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.*;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatisticsManagerServer.class)
public class MixinStatisticsManagerServer
    extends StatisticsManager implements IStatisticsManagerServerExt {
  @Shadow private MinecraftServer server;
  @Unique private boolean hasUnsentAchievement;
  @Shadow private Set<StatBase> dirty;
  @Shadow private int lastStatRequest;

  /**
   * @author Komichi
   * @reason Broadcast achievement messages
   */
  @Overwrite
  public void unlockAchievement(EntityPlayer playerIn, StatBase statIn,
                                int p_150873_3_) {
    int prevValue = readStat(statIn);

    super.unlockAchievement(playerIn, statIn, p_150873_3_);
    this.dirty.add(statIn);

    if (((IStatBaseExt)statIn).isAchievement()) {
      if (prevValue == 0 && p_150873_3_ > 0) {
        hasUnsentAchievement = true;

        if (((IMinecraftServerExt)server).isAnnouncingPlayerAchievements()) {
          server.getPlayerList().sendMessage(new TextComponentTranslation(
              "chat.type.achievement", playerIn.getDisplayName(),
              ((IStatBaseExt)statIn).createChatComponent()));
        }
      }

      if (prevValue > 0 && p_150873_3_ == 0) {
        hasUnsentAchievement = true;

        if (((IMinecraftServerExt)server).isAnnouncingPlayerAchievements()) {
          server.getPlayerList().sendMessage(new TextComponentTranslation(
              "chat.type.achievement.taken", playerIn.getDisplayName(),
              ((IStatBaseExt)statIn).createChatComponent()));
        }
      }
    }
  }

  @Inject(method = "getDirty", at = @At("RETURN"))
  private void mixin2(CallbackInfoReturnable<Set<StatBase>> cir) {
    hasUnsentAchievement = false;
  }

  /**
   * @author Komichi
   * @reason Send unsent achievements
   */
  @Overwrite
  public void sendStats(EntityPlayerMP player) {
    int i = this.server.getTickCounter();
    Map<StatBase, Integer> map = Maps.<StatBase, Integer>newHashMap();

    if (hasUnsentAchievement || i - this.lastStatRequest > 300) {
      this.lastStatRequest = i;

      for (StatBase statbase : this.getDirty()) {
        map.put(statbase, Integer.valueOf(this.readStat(statbase)));
      }
    }

    player.connection.sendPacket(new SPacketStatistics(map));
  }

  @Shadow
  private Set<StatBase> getDirty() {
    return null;
  }

  public void sendAchievements(EntityPlayerMP player) {
    Map<StatBase, Integer> map = Maps.newHashMap();

    for (Achievement a : AchievementList.ACHIEVEMENTS) {
      if (((IStatisticsManagerExt)this).hasAchievementUnlocked(a)) {
        map.put(a, readStat(a));
        dirty.remove(a);
      }
    }

    player.connection.sendPacket(new SPacketStatistics(map));
  }

  public boolean hasUnsentAchievement() { return hasUnsentAchievement; }
}
