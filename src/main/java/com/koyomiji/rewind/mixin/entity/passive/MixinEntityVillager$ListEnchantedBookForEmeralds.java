package com.koyomiji.rewind.mixin.entity.passive;

import com.koyomiji.rewind.EnchantmentHandler;
import com.koyomiji.rewind.FilteredRegistryNamaspaced;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
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
        Enchantment.REGISTRY, EnchantmentHandler::shouldKeepEnchantment);
  }
}
