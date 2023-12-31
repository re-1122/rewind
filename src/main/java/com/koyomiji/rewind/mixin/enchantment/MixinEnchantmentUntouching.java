package com.koyomiji.rewind.mixin.enchantment;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentUntouching;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EnchantmentUntouching.class)
public class MixinEnchantmentUntouching extends Enchantment {
  protected MixinEnchantmentUntouching(Rarity rarityIn,
                                       EnumEnchantmentType typeIn,
                                       EntityEquipmentSlot[] slots) {
    super(rarityIn, typeIn, slots);
  }

  public boolean canApply(ItemStack stack) {
    if (ReWindConfig.allowSilkTouchOnShears) {
      return stack.getItem() == Items.SHEARS || super.canApply(stack);
    }

    return super.canApply(stack);
  }
}
