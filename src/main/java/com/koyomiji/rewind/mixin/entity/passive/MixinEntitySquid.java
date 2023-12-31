package com.koyomiji.rewind.mixin.entity.passive;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntitySquid.class)
public class MixinEntitySquid {
  @Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
  protected void mixin0(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.noSquidSound) {
      cir.setReturnValue(null);
    }
  }

  @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
  protected void mixin1(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.noSquidSound) {
      cir.setReturnValue(null);
    }
  }

  @Inject(method = "getDeathSound", at = @At("HEAD"), cancellable = true)
  protected void mixin2(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.noSquidSound) {
      cir.setReturnValue(null);
    }
  }
}
