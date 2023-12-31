package com.koyomiji.rewind.mixin.entity.player;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
  @Inject(method = "isRowingBoat", at = @At("HEAD"), cancellable = true)
  private void isRowingBoat(CallbackInfoReturnable<Boolean> cir) {
    if (ReWindConfig.enableInteractionWhenRowingBoat) {
      cir.setReturnValue(false);
    }
  }
}
