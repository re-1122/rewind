package com.koyomiji.rewind.mixin.entity.monster;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntitySnowman.class)
public class MixinEntitySnowman {
  @Inject(method = "getAmbientSound", at = @At(value = "HEAD"),
          cancellable = true)
  private void
  mixin0(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.noSnowmanSound) {
      cir.setReturnValue(null);
    }
  }

  @Inject(method = "getHurtSound", at = @At(value = "HEAD"), cancellable = true)
  private void mixin1(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.noSnowmanSound) {
      cir.setReturnValue(null);
    }
  }

  @Inject(method = "getDeathSound", at = @At(value = "HEAD"),
          cancellable = true)
  private void
  mixin2(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.noSnowmanSound) {
      cir.setReturnValue(null);
    }
  }
}
