package com.koyomiji.rewind.mixin.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlotFurnaceOutput.class)
public class MixinSlotFurnaceOutput {
  @Shadow @Final private EntityPlayer player;

  @Inject(method = "onCrafting(Lnet/minecraft/item/ItemStack;)V",
          at = @At("RETURN"))
  private void
  mixin(ItemStack stack, CallbackInfo ci) {
    if (stack.getItem() == Items.IRON_INGOT) {
      this.player.addStat(AchievementList.ACQUIRE_IRON);
    }

    if (stack.getItem() == Items.COOKED_FISH) {
      this.player.addStat(AchievementList.COOK_FISH);
    }
  }
}
