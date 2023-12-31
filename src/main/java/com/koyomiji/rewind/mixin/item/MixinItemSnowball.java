package com.koyomiji.rewind.mixin.item;

import com.koyomiji.rewind.entity.ISidedShootable;
import com.koyomiji.rewind.util.HandHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemSnowball.class)
public class MixinItemSnowball {
  @Redirect(
      method = "onItemRightClick",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/projectile/EntitySnowball;shoot(Lnet/minecraft/entity/Entity;FFFFF)V")
      )
  private void
  mixin(EntitySnowball self, Entity entityThrower, float rotationPitchIn,
        float rotationYawIn, float pitchOffset, float velocity,
        float inaccuracy) {}

  @Inject(
      method = "onItemRightClick",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/projectile/EntitySnowball;shoot(Lnet/minecraft/entity/Entity;FFFFF)V")
      ,
      locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixin2(World worldIn, EntityPlayer playerIn, EnumHand handIn,
         CallbackInfoReturnable<ActionResult<ItemStack>> cir,
         ItemStack itemstack, EntitySnowball entitysnowball) {
    EnumHandSide side =
        HandHelper.toHandSide(playerIn.getPrimaryHand(), handIn);
    ((ISidedShootable)entitysnowball)
        .shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F,
               1.5F, 1.0F, side);
  }
}
