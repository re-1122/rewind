package com.koyomiji.rewind.mixin.client.network;

import com.google.common.collect.Sets;
import com.koyomiji.rewind.ReWind;
import com.koyomiji.rewind.client.IMinecraftExt;
import com.koyomiji.rewind.client.settings.IGameSettingsExt;
import com.koyomiji.rewind.stats.IStatBaseExt;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.SPacketStatistics;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
  @Shadow private boolean hasStatistics;
  @Shadow private Minecraft client;

  @Redirect(method = "handleStatistics",
            at = @At(value = "INVOKE",
                     target = "Ljava/util/Map;entrySet()Ljava/util/Set;"))
  private Set
  mixin(Map instance) {
    return Sets.newHashSet();
  }

  @Inject(
      method = "handleStatistics",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
          shift = At.Shift.AFTER))
  private void
  mixin2(SPacketStatistics packetIn, CallbackInfo ci) {
    boolean flag = false;
    GuiAchievement guiAchievement = ((IMinecraftExt)client).getGuiAchievement();

    for (Map.Entry<StatBase, Integer> entry :
         packetIn.getStatisticMap().entrySet()) {
      StatBase statbase = entry.getKey();
      int k = entry.getValue();

      if (((IStatBaseExt)statbase).isAchievement() && k > 0) {
        if (hasStatistics &&
            client.player.getStatFileWriter().readStat(statbase) == 0) {
          Achievement achievement = (Achievement)statbase;
          guiAchievement.displayAchievement(achievement);

          if (statbase == AchievementList.OPEN_INVENTORY) {
            ((IGameSettingsExt)client.gameSettings)
                .setShowInventoryAchievementHint(false);
            client.gameSettings.saveOptions();
          }
        }

        flag = true;
      }

      this.client.player.getStatFileWriter().unlockAchievement(
          this.client.player, statbase, k);
    }

    if (!hasStatistics && !flag &&
        ((IGameSettingsExt)client.gameSettings)
            .getShowInventoryAchievementHint()) {
      guiAchievement.displayUnformattedAchievement(
          AchievementList.OPEN_INVENTORY);
    }
  }
}
