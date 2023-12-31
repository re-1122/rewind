package com.koyomiji.rewind.mixin.entity.monster;

import com.koyomiji.rewind.ReWind;
import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractSkeleton.class)
public abstract class MixinAbstractSkeleton extends EntityMob {
  public MixinAbstractSkeleton(World worldIn) { super(worldIn); }

  @Override
  public void onDeath(DamageSource cause) {
    super.onDeath(cause);

    if (cause.getImmediateSource() instanceof EntityArrow &&
        cause.getTrueSource() instanceof EntityPlayer) {
      EntityPlayer p = (EntityPlayer)cause.getTrueSource();
      double dx = p.posX - posX;
      double dz = p.posZ - posZ;

      if (dx * dx + dz * dz >= 2500) {
        p.addStat(AchievementList.SNIPE_SKELETON);
      }
    }
  }
}
