package com.koyomiji.rewind.mixin.network;

import com.koyomiji.rewind.network.CPacketClientStatusHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.stats.AchievementList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayServer.class)
public class MixinNetHandlerPlayServer {
  @Shadow public EntityPlayerMP player;

  @Inject(method = "processClientStatus", at = @At(value = "TAIL"))
  private void mixin(CPacketClientStatus packetIn, CallbackInfo ci) {
    if (packetIn.getStatus() ==
        CPacketClientStatusHelper.OPEN_INVENTORY_ACHIEVEMENT) {
      player.addStat(AchievementList.OPEN_INVENTORY);
    }
  }
}
