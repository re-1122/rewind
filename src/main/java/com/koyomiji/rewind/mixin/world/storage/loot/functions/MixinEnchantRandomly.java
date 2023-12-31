package com.koyomiji.rewind.mixin.world.storage.loot.functions;

import com.google.common.collect.Lists;
import com.koyomiji.rewind.EnchantmentHandler;
import com.koyomiji.rewind.FilteredRegistryNamaspaced;
import java.util.List;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.functions.EnchantRandomly;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantRandomly.class)
public class MixinEnchantRandomly {
  @Redirect(
      method = "apply",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/enchantment/Enchantment;REGISTRY:Lnet/minecraft/util/registry/RegistryNamespaced;")
      )
  private RegistryNamespaced<ResourceLocation, Enchantment>
  mixin() {
    return new FilteredRegistryNamaspaced<>(
        Enchantment.REGISTRY, EnchantmentHandler::shouldKeepEnchantment);
  }
}
