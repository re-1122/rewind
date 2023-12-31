package com.koyomiji.rewind.mixin.client.gui.toasts;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.toasts.GuiToast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiToast.class)
public class MixinGuiToast {
  @Inject(method = "drawToast", at = @At(value = "HEAD"), cancellable = true)
  public void mixin(ScaledResolution resolution, CallbackInfo ci) {
    if (ReWindConfig.enableAchievement) {
      ci.cancel();
    }
  }
}
