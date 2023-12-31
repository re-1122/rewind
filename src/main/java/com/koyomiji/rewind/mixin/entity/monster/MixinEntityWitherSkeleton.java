package com.koyomiji.rewind.mixin.entity.monster;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityWitherSkeleton.class)
public class MixinEntityWitherSkeleton {
  @Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
  protected void mixin0(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.traditionalWitherSkeletonSound) {
      cir.setReturnValue(SoundEvents.ENTITY_SKELETON_AMBIENT);
    }
  }

  @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
  protected void mixin1(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.traditionalWitherSkeletonSound) {
      cir.setReturnValue(SoundEvents.ENTITY_SKELETON_HURT);
    }
  }

  @Inject(method = "getDeathSound", at = @At("HEAD"), cancellable = true)
  protected void mixin2(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.traditionalWitherSkeletonSound) {
      cir.setReturnValue(SoundEvents.ENTITY_SKELETON_DEATH);
    }
  }

  @Inject(method = "getStepSound", at = @At("HEAD"), cancellable = true)
  protected void mixin3(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.traditionalWitherSkeletonSound) {
      cir.setReturnValue(SoundEvents.ENTITY_SKELETON_DEATH);
    }
  }
}
