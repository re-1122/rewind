package com.koyomiji.rewind.mixin.entity.passive;

import com.koyomiji.rewind.util.EnchantmentUtil;
import com.koyomiji.rewind.util.FilteredRegistryNamaspaced;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityVillager.ListEnchantedBookForEmeralds.class)
public class MixinEntityVillager$ListEnchantedBookForEmeralds {
  @Redirect(
      method = "addMerchantRecipe",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/enchantment/Enchantment;REGISTRY:Lnet/minecraft/util/registry/RegistryNamespaced;")
      )
  private RegistryNamespaced<ResourceLocation, Enchantment>
  mixin() {
    return new FilteredRegistryNamaspaced<>(
        Enchantment.REGISTRY, EnchantmentUtil::shouldKeepEnchantment);
  }
}
