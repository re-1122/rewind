package com.koyomiji.rewind.mixin.block;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.block.BlockSkull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.AchievementList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockSkull.class)
public class MixinBlockSkull {
  @Redirect(
      method = "checkWitherSpawn",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/advancements/critereon/SummonedEntityTrigger;trigger(Lnet/minecraft/entity/player/EntityPlayerMP;Lnet/minecraft/entity/Entity;)V")
      )
  private void
  redirect1(SummonedEntityTrigger instance, EntityPlayerMP player,
            Entity entity) {
    player.addStat(AchievementList.SPAWN_WITHER);
    instance.trigger(player, entity);
  }
}
