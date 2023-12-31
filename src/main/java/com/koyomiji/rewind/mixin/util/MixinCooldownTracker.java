package com.koyomiji.rewind.mixin.util;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.item.Item;
import net.minecraft.util.CooldownTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CooldownTracker.class)
public class MixinCooldownTracker {
  @Inject(method = "getCooldown", at = @At("HEAD"), cancellable = true)
  private void mixin(Item itemIn, float partialTicks,
                     CallbackInfoReturnable<Float> cir) {
    if (ReWindConfig.disableItemCooldown) {
      cir.setReturnValue(0.0F);
    }
  }
}
