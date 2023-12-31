package com.koyomiji.rewind.mixin.item;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemSword.class)
public class MixinItemSword extends Item {
  @Shadow private float attackDamage;

  @Inject(method = "<init>(Lnet/minecraft/item/Item$ToolMaterial;)V",
          at = @At(value = "RETURN"), require = 1)
  private void
  mixin(Item.ToolMaterial material, CallbackInfo ci) {
    if (ReWindConfig.oldCombat) {
      this.attackDamage = 4.0F + material.getAttackDamage();
    }
  }

  public EnumAction getItemUseAction(ItemStack stack) {
    if (ReWindConfig.oldCombat) {
      return EnumAction.BLOCK;
    }

    return super.getItemUseAction(stack);
  }

  public int getMaxItemUseDuration(ItemStack stack) {
    if (ReWindConfig.oldCombat) {
      return 72000;
    }

    return super.getMaxItemUseDuration(stack);
  }

  public ActionResult<ItemStack>
  onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    if (ReWindConfig.oldCombat) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      playerIn.setActiveHand(handIn);
      return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    return super.onItemRightClick(worldIn, playerIn, handIn);
  }
}
