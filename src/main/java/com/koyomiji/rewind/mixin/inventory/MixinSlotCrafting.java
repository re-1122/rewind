package com.koyomiji.rewind.mixin.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.*;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlotCrafting.class)
public class MixinSlotCrafting {
  @Shadow @Final private EntityPlayer player;

  @Inject(method = "onCrafting(Lnet/minecraft/item/ItemStack;)V",
          at = @At("RETURN"))
  private void
  mixin(ItemStack stack, CallbackInfo ci) {
    if (stack.getItem() == Item.getItemFromBlock(Blocks.CRAFTING_TABLE)) {
      this.player.addStat(AchievementList.BUILD_WORK_BENCH);
    }

    if (stack.getItem() instanceof ItemPickaxe) {
      this.player.addStat(AchievementList.BUILD_PICKAXE);
    }

    if (stack.getItem() == Item.getItemFromBlock(Blocks.FURNACE)) {
      this.player.addStat(AchievementList.BUILD_FURNACE);
    }

    if (stack.getItem() instanceof ItemHoe) {
      this.player.addStat(AchievementList.BUILD_HOE);
    }

    if (stack.getItem() == Items.BREAD) {
      this.player.addStat(AchievementList.MAKE_BREAD);
    }

    if (stack.getItem() == Items.CAKE) {
      this.player.addStat(AchievementList.BAKE_CAKE);
    }

    if (stack.getItem() instanceof ItemPickaxe &&
        !((ItemPickaxe)stack.getItem()).getToolMaterialName().equals("WOOD")) {
      this.player.addStat(AchievementList.BUILD_BETTER_PICKAXE);
    }

    if (stack.getItem() instanceof ItemSword) {
      this.player.addStat(AchievementList.BUILD_SWORD);
    }

    if (stack.getItem() == Item.getItemFromBlock(Blocks.ENCHANTING_TABLE)) {
      this.player.addStat(AchievementList.ENCHANTMENTS);
    }

    if (stack.getItem() == Item.getItemFromBlock(Blocks.BOOKSHELF)) {
      this.player.addStat(AchievementList.BOOKCASE);
    }
  }
}
