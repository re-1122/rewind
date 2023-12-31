package com.koyomiji.rewind.mixin.entity.player;

import com.google.common.collect.Sets;
import com.koyomiji.rewind.biome.IBiomeNameProvider;
import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.stats.IStatisticsManagerExt;
import com.koyomiji.rewind.stats.IStatisticsManagerServerExt;
import com.koyomiji.rewind.world.biome.BiomeExt;
import com.mojang.authlib.GameProfile;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatisticsManagerServer;
import net.minecraft.util.JsonSerializableSet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.ITeleporter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerMP.class)
public abstract class MixinEntityPlayerMP
    extends EntityPlayer implements IContainerListener {
  @Shadow private StatisticsManagerServer statsFile;

  @Shadow public abstract StatisticsManagerServer getStatFile();

  @Shadow private boolean seenCredits;

  public MixinEntityPlayerMP(World worldIn, GameProfile playerProfile) {
    super(worldIn, playerProfile);
  }

  @ModifyVariable(
      method = "sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
      name = "actionBar", require = 1, at = @At(value = "LOAD"))
  private boolean
  mixin(boolean actionBar) {
    if (ReWindConfig.showOverlayMessageInChat) {
      return false;
    }

    return actionBar;
  }

  @Inject(method = "addStat", at = @At(value = "RETURN"))
  private void mixin2(StatBase stat, int amount, CallbackInfo ci) {
    if (stat != null) {
      if (((IStatisticsManagerServerExt)statsFile).hasUnsentAchievement()) {
        statsFile.sendStats((EntityPlayerMP)(Object)this);
      }
    }
  }

  @Inject(method = "takeStat", at = @At(value = "RETURN"))
  private void mixin3(StatBase stat, CallbackInfo ci) {
    if (stat != null) {
      if (((IStatisticsManagerServerExt)statsFile).hasUnsentAchievement()) {
        statsFile.sendStats((EntityPlayerMP)(Object)this);
      }
    }
  }

  @Inject(
      method = "onUpdateEntity",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/advancements/critereon/PositionTrigger;trigger(Lnet/minecraft/entity/player/EntityPlayerMP;)V")
      )
  private void
  mixin4(CallbackInfo ci) {
    if (this.ticksExisted % 100 == 0 &&
        !((IStatisticsManagerExt)this.getStatFile())
             .hasAchievementUnlocked(AchievementList.EXPLORE_ALL_BIOMES)) {
      this.updateBiomesExplored();
    }
  }

  protected void updateBiomesExplored() {
    Biome biome = this.world.getBiome(new BlockPos(
        MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ)));
    String biomeName = ((IBiomeNameProvider)biome).rewind$getBiomeName();
    JsonSerializableSet set =
        ((IStatisticsManagerExt)this.getStatFile())
            .getProgress(AchievementList.EXPLORE_ALL_BIOMES);

    if (set == null) {
      set = ((IStatisticsManagerExt)this.getStatFile())
                .setProgress(AchievementList.EXPLORE_ALL_BIOMES,
                             new JsonSerializableSet());
    }

    set.add(biomeName);

    if (((IStatisticsManagerExt)this.getStatFile())
            .canUnlockAchievement(AchievementList.EXPLORE_ALL_BIOMES) &&
        set.size() >= BiomeExt.EXPLORATION_BIOMES_LIST.size()) {
      Set<Biome> biomeSet = Sets.newHashSet(BiomeExt.EXPLORATION_BIOMES_LIST);

      for (String b1 : set) {
        Iterator<Biome> biomeIt = biomeSet.iterator();

        while (biomeIt.hasNext()) {
          Biome b2 = biomeIt.next();

          if (b2.getBiomeName().equals(b1)) {
            biomeIt.remove();
          }
        }

        if (biomeSet.isEmpty()) {
          break;
        }
      }

      if (biomeSet.isEmpty()) {
        this.addStat(AchievementList.EXPLORE_ALL_BIOMES);
      }
    }
  }

  @Inject(
      method = "changeDimension",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/network/NetHandlerPlayServer;sendPacket(Lnet/minecraft/network/Packet;)V",
          ordinal = 0))
  private void
  mixin5(int dimensionIn, ITeleporter teleporter,
         CallbackInfoReturnable<Entity> cir) {
    if (!this.seenCredits) {
      addStat(AchievementList.THE_END2);
    }
  }

  @Inject(
      method = "changeDimension",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/server/management/PlayerList;transferPlayerToDimension(Lnet/minecraft/entity/player/EntityPlayerMP;ILnet/minecraftforge/common/util/ITeleporter;)V",
          ordinal = 0),
      remap = false)
  private void
  mixin6(int dimensionIn, ITeleporter teleporter,
         CallbackInfoReturnable<Entity> cir) {
    if (this.dimension == 0 && dimensionIn == 1) {
      addStat(AchievementList.THE_END);
    } else {
      addStat(AchievementList.PORTAL);
    }
  }
}
