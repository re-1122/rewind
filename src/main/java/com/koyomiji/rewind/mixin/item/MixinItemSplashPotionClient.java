package com.koyomiji.rewind.mixin.item;

import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemSplashPotion.class)
public class MixinItemSplashPotionClient {
  public void addInformation(ItemStack stack, World worldIn,
                             List<String> tooltip, ITooltipFlag flagIn) {
    PotionUtils.addPotionTooltip(stack, tooltip, 3 / 4.f);
  }
}
