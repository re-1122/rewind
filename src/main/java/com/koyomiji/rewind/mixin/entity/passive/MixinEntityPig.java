package com.koyomiji.rewind.mixin.entity.passive;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityPig.class)
public abstract class MixinEntityPig extends EntityAnimal {
  public MixinEntityPig(World worldIn) { super(worldIn); }

  @Override
  public void fall(float distance, float damageMultiplier) {
    super.fall(distance, damageMultiplier);

    if (distance > 5) {
      for (EntityPlayer p :
           this.getRecursivePassengersByType(EntityPlayer.class)) {
        p.addStat(AchievementList.FLY_PIG);
      }
    }
  }
}
