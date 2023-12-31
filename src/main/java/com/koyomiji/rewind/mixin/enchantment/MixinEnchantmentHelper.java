package com.koyomiji.rewind.mixin.enchantment;

import com.google.common.collect.Lists;
import com.koyomiji.rewind.EnchantmentHandler;
import com.koyomiji.rewind.FilteredRegistryNamaspaced;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper {
  @Redirect(
      method = "getEnchantmentDatas",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/enchantment/Enchantment;REGISTRY:Lnet/minecraft/util/registry/RegistryNamespaced;")
      )
  private static RegistryNamespaced<ResourceLocation, Enchantment>
  mixin() {
    return new FilteredRegistryNamaspaced<>(
        Enchantment.REGISTRY, EnchantmentHandler::shouldKeepEnchantment);
  }
}