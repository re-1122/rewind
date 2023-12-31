package com.koyomiji.rewind.mixin.enchantment;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentArrowInfinite.class)
public abstract class MixinArrowInfiniteEnchantment extends Enchantment {
  protected MixinArrowInfiniteEnchantment(Rarity rarityIn,
                                          EnumEnchantmentType typeIn,
                                          EntityEquipmentSlot[] slots) {
    super(rarityIn, typeIn, slots);
  }

  @Inject(method = "canApplyTogether", at = @At(value = "HEAD"),
          cancellable = true)
  public void
  mixin(Enchantment ench, CallbackInfoReturnable<Boolean> cir) {
    if (ReWindConfig.allowInfinityMendingBow) {
      cir.setReturnValue(super.canApplyTogether(ench));
    }
  }
}
