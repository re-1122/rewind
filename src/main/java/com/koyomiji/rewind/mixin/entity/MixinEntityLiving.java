package com.koyomiji.rewind.mixin.entity;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityLiving.class)
public abstract class MixinEntityLiving extends EntityLivingBase {
  public MixinEntityLiving(World worldIn) { super(worldIn); }

  @Inject(
      method = "updateEquipmentIfNeeded",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/EntityLiving;setItemStackToSlot(Lnet/minecraft/inventory/EntityEquipmentSlot;Lnet/minecraft/item/ItemStack;)V",
          ordinal = 0),
      locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixin1(EntityItem itemEntity, CallbackInfo ci, ItemStack itemstack) {
    if (itemstack.getItem() == Items.DIAMOND &&
        itemEntity.getThrower() != null) {
      EntityPlayer thrower =
          this.world.getPlayerEntityByName(itemEntity.getThrower());

      if (thrower != null) {
        thrower.addStat(AchievementList.DIAMONDS_TO_YOU);
      }
    }
  }
}
