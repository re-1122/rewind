package com.koyomiji.rewind.mixin.item;

import com.koyomiji.rewind.entity.ISidedShootable;
import com.koyomiji.rewind.util.HandUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemBow.class)
public class MixinItemBow {
  @Redirect(
      method = "onPlayerStoppedUsing",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/projectile/EntityArrow;shoot(Lnet/minecraft/entity/Entity;FFFFF)V")
      )
  private void
  mixin(EntityArrow instance, Entity shooter, float pitch, float yaw,
        float p_184547_4_, float velocity, float inaccuracy) {}

  @Inject(
      method = "onPlayerStoppedUsing",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/projectile/EntityArrow;shoot(Lnet/minecraft/entity/Entity;FFFFF)V")
      ,
      locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixin2(ItemStack stack, World worldIn, EntityLivingBase entityLiving,
         int timeLeft, CallbackInfo ci, EntityPlayer entityplayer, boolean flag,
         ItemStack itemstack, int i, float f, boolean flag1,
         ItemArrow itemarrow, EntityArrow entityarrow) {
    EnumHandSide side = HandUtil.toHandSide(entityplayer.getPrimaryHand(),
                                              entityplayer.getActiveHand());
    ((ISidedShootable)entityarrow)
        .shoot(entityplayer, entityplayer.rotationPitch,
               entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F, side);
  }
}
