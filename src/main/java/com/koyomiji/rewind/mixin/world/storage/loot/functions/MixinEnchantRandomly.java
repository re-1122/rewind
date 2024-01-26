package com.koyomiji.rewind.mixin.world.storage.loot.functions;

import com.koyomiji.rewind.util.EnchantmentUtil;
import com.koyomiji.rewind.util.FilteredRegistryNamaspaced;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.storage.loot.functions.EnchantRandomly;
import org.spongepowered.asm.mixin.Mixin;
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
        Enchantment.REGISTRY, EnchantmentUtil::shouldKeepEnchantment);
  }
}
