package com.koyomiji.rewind.mixin.enchantment;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Enchantment.class)
public class MixinEnchantment {
  @Inject(method = "getTranslatedName", at = @At("RETURN"), cancellable = true,
          locals = LocalCapture.CAPTURE_FAILHARD)
  public void
  getTranslatedName(int level, CallbackInfoReturnable<String> cir, String s) {
    if (ReWindConfig.alwaysShowEnchantmentLevel) {
      cir.setReturnValue(s + " " +
                         I18n.translateToLocal("enchantment.level." + level));
    }
  }

  @Shadow
  public String getName() {
    return null;
  }
}
