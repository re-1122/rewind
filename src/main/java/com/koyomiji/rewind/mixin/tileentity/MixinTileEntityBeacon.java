package com.koyomiji.rewind.mixin.tileentity;

import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TileEntityBeacon.class)
public abstract class MixinTileEntityBeacon extends TileEntityLockable {
  @Shadow private int levels;

  @Inject(
      method = "updateSegmentColors",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/advancements/critereon/ConstructBeaconTrigger;trigger(Lnet/minecraft/entity/player/EntityPlayerMP;Lnet/minecraft/tileentity/TileEntityBeacon;)V")
      ,
      locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixinUpdateSegmentColors(
      CallbackInfo ci, int i, int j, int k, int l,
      TileEntityBeacon.BeamSegment tileentitybeacon$beamsegment, boolean flag,
      BlockPos.MutableBlockPos blockpos$mutableblockpos, Iterator var8,
      EntityPlayerMP entityplayermp) {
    if (this.levels == 4) {
      entityplayermp.addStat(AchievementList.FULL_BEACON);
    }
  }
}
