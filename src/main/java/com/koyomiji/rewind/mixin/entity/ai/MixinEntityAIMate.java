package com.koyomiji.rewind.mixin.entity.ai;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.advancements.critereon.BredAnimalsTrigger;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.AchievementList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityAIMate.class)
public class MixinEntityAIMate {
  @Redirect(
      method = "spawnBaby",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/advancements/critereon/BredAnimalsTrigger;trigger(Lnet/minecraft/entity/player/EntityPlayerMP;Lnet/minecraft/entity/passive/EntityAnimal;Lnet/minecraft/entity/passive/EntityAnimal;Lnet/minecraft/entity/EntityAgeable;)V")
      )

  private void
  redirect1(BredAnimalsTrigger instance, EntityPlayerMP player,
            EntityAnimal parent1, EntityAnimal parent2, EntityAgeable child) {
    if (parent1 instanceof EntityCow) {
      player.addStat(AchievementList.BREED_COW);
    }

    instance.trigger(player, parent1, parent2, child);
  }
}
