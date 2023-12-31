package com.koyomiji.rewind.mixin.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemAppleGold.class)
public class MixinItemAppleGold {
  @Inject(
      method = "onFoodEaten",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/player/EntityPlayer;addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V",
          ordinal = 0))
  private void
  mixin(ItemStack stack, World worldIn, EntityPlayer player, CallbackInfo ci) {
    player.addStat(AchievementList.OVERPOWERED);
  }
}
