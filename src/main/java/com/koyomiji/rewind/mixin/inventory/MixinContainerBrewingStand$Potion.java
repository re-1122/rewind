package com.koyomiji.rewind.mixin.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.inventory.ContainerBrewingStand$Potion")
public class MixinContainerBrewingStand$Potion {
  @Inject(
      method = "onTake",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/advancements/critereon/BrewedPotionTrigger;trigger(Lnet/minecraft/entity/player/EntityPlayerMP;Lnet/minecraft/potion/PotionType;)V")
      )
  private void
  mixin(EntityPlayer thePlayer, ItemStack stack,
        CallbackInfoReturnable<ItemStack> cir) {
    thePlayer.addStat(AchievementList.POTION);
  }
}
