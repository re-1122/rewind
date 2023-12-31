package com.koyomiji.rewind.mixin.entity.monster;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityGhast.class)
public abstract class MixinEntityGhast extends EntityFlying {
  public MixinEntityGhast(World worldIn) { super(worldIn); }

  @Shadow
  public abstract boolean attackEntityFrom(DamageSource source, float amount);

  @Inject(
      method = "attackEntityFrom",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/EntityFlying;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z",
          ordinal = 0, shift = At.Shift.AFTER),
      locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixin(DamageSource source, float amount,
        CallbackInfoReturnable<Boolean> cir) {
    if (ReWindConfig.enableAchievement) {
      ((EntityPlayer)source.getTrueSource()).addStat(AchievementList.GHAST);
    }
  }
}
