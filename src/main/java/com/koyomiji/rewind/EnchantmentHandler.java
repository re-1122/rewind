package com.koyomiji.rewind;

import com.koyomiji.rewind.config.ReWindConfig;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EnchantmentHandler {
  public static boolean shouldKeepEnchantment(Enchantment enchantment) {
    return !shouldRemoveEnchantment(enchantment);
  }

  public static boolean shouldRemoveEnchantment(Enchantment enchantment) {
    if (ReWindConfig.removeSweepingEdge &&
        enchantment == Enchantments.SWEEPING) {
      return true;
    }

    if (ReWindConfig.removeCursedEnchantments &&
        isCurseEnchantment(enchantment)) {
      return true;
    }

    return false;
  }

  public static boolean isCurseEnchantment(Enchantment enchantment) {
    return (enchantment == Enchantments.BINDING_CURSE ||
            enchantment == Enchantments.VANISHING_CURSE);
  }

  private static final Random random = new Random();

  public static int getEnchantmentModifierDamageOld(Iterable<ItemStack> stacks,
                                                    DamageSource source) {
    int modifier =
        EnchantmentHelper.getEnchantmentModifierDamage(stacks, source);

    if (modifier > 25) {
      modifier = 25;
    }

    if (modifier < 0) {
      modifier = 0;
    }

    return ((modifier + 1) / 2) + random.nextInt(modifier / 2 + 1);
  }
}
