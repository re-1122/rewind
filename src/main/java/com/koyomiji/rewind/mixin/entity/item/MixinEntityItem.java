package com.koyomiji.rewind.mixin.entity.item;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityItem.class)
public abstract class MixinEntityItem {
  @Shadow public abstract String getThrower();

  @Inject(
      method = "onCollideWithPlayer",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraftforge/fml/common/FMLCommonHandler;firePlayerItemPickupEvent(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/item/EntityItem;Lnet/minecraft/item/ItemStack;)V",
          remap = false),
      locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixin(EntityPlayer entityIn, CallbackInfo ci, ItemStack itemstack, Item item,
        int i, int hook, ItemStack clone) {
    if (item == Item.getItemFromBlock(Blocks.LOG) ||
        item == Item.getItemFromBlock(Blocks.LOG2)) {
      entityIn.addStat(AchievementList.MINE_WOOD);
    }

    if (item == Items.LEATHER) {
      entityIn.addStat(AchievementList.KILL_COW);
    }

    if (item == Items.DIAMOND) {
      entityIn.addStat(AchievementList.DIAMONDS);
    }

    if (item == Items.BLAZE_ROD) {
      entityIn.addStat(AchievementList.BLAZE_ROD);
    }

    if (item == Items.DIAMOND && getThrower() != null) {
      EntityPlayer thrower = entityIn.world.getPlayerEntityByName(getThrower());

      if (thrower != null && thrower != entityIn) {
        thrower.addStat(AchievementList.DIAMONDS_TO_YOU);
      }
    }
  }
}
