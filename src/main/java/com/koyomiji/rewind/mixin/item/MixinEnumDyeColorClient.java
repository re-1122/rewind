package com.koyomiji.rewind.mixin.item;

import com.koyomiji.rewind.item.DyeUtil;
import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.item.EnumDyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnumDyeColor.class)
public class MixinEnumDyeColorClient {
  @Inject(method = "getColorValue", at = @At("HEAD"), cancellable = true)
    public void mixin(CallbackInfoReturnable<Integer> cir) {
      if (ReWindConfig.looksAndFeels.traditionalColors) {
        cir.setReturnValue(DyeUtil.getMapColor((EnumDyeColor)(Object)this).colorValue);
      }
    }
}
