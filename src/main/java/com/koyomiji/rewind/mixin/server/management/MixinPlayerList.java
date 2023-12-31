package com.koyomiji.rewind.mixin.server.management;

import com.koyomiji.rewind.stats.IStatisticsManagerServerExt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.management.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class MixinPlayerList {
  @Inject(
      method = "initializeConnectionToPlayer",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/stats/StatisticsManagerServer;markAllDirty()V",
          shift = At.Shift.AFTER))
  private void
  mixin(NetworkManager netManager, EntityPlayerMP playerIn,
        NetHandlerPlayServer nethandlerplayserver, CallbackInfo ci) {
    ((IStatisticsManagerServerExt)playerIn.getStatFile())
        .sendAchievements(playerIn);
  }
}
