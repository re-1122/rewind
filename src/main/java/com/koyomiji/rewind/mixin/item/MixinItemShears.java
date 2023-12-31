package com.koyomiji.rewind.mixin.item;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemShears.class)
public class MixinItemShears {
  @Redirect(
      method = "onBlockDestroyed",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/item/ItemStack;damageItem(ILnet/minecraft/entity/EntityLivingBase;)V")
      )
  public void
  mixin(ItemStack itemStack, int amount, EntityLivingBase entityIn) {
    if (ReWindConfig.preventShearsDamageForRegularBlocks) {
      return;
    }

    itemStack.damageItem(amount, entityIn);
  }
}
