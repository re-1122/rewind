package com.koyomiji.rewind.mixin.entity.boss;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityWither.class)
public class MixinEntityWither extends EntityMob {
  public MixinEntityWither(World worldIn) { super(worldIn); }

  @Inject(method = "dropFewItems", at = @At("RETURN"))
  private void inject1(CallbackInfo ci) {
    if (!this.world.isRemote) {
      for (EntityPlayer p : this.world.getEntitiesWithinAABB(
               EntityPlayer.class,
               this.getEntityBoundingBox().grow(50, 100, 50))) {
        p.addStat(AchievementList.KILL_WITHER);
      }
    }
  }
}
