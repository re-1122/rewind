package com.koyomiji.rewind.mixin.item;

import com.google.common.collect.Multimap;
import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public class MixinItemStack {
  @Redirect(
      method = "getTooltip",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/item/ItemStack;getAttributeModifiers(Lnet/minecraft/inventory/EntityEquipmentSlot;)Lcom/google/common/collect/Multimap;")
      )
  private Multimap<String, AttributeModifier>
  getDisplayName(ItemStack instance, EntityEquipmentSlot attributemodifier) {
    Multimap<String, AttributeModifier> map =
        instance.getAttributeModifiers(attributemodifier);

    if (ReWindConfig.oldDamageCalculation) {
      map.removeAll(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName());
    }

    if (ReWindConfig.oldCombat) {
      map.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName());
    }

    return map;
  }

  @Redirect(
      method = "getTooltip",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/ai/attributes/IAttributeInstance;getBaseValue()D")
      )
  private double
  mixin(IAttributeInstance instance) {
    if (ReWindConfig.oldWeaponTooltip) {
      return 0;
    }

    return instance.getBaseValue();
  }

  @ModifyVariable(method = "getTooltip", at = @At(value = "LOAD"),
                  name = "flag")
  private boolean
  mixin2(boolean original) {
    if (ReWindConfig.oldWeaponTooltip) {
      return false;
    }

    return original;
  }
}
