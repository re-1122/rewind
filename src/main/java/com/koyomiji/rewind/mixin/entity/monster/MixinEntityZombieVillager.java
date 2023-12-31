package com.koyomiji.rewind.mixin.entity.monster;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityZombieVillager.class)
public class MixinEntityZombieVillager {
  @Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
  public void mixin0(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.traditionalZombieVillagerSound) {
      cir.setReturnValue(SoundEvents.ENTITY_ZOMBIE_AMBIENT);
    }
  }

  @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
  public void mixin2(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.traditionalZombieVillagerSound) {
      cir.setReturnValue(SoundEvents.ENTITY_ZOMBIE_HURT);
    }
  }

  @Inject(method = "getDeathSound", at = @At("HEAD"), cancellable = true)
  public void mixin3(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.traditionalZombieVillagerSound) {
      cir.setReturnValue(SoundEvents.ENTITY_ZOMBIE_DEATH);
    }
  }
  @Inject(method = "getStepSound", at = @At("HEAD"), cancellable = true)
  public void mixin4(CallbackInfoReturnable<SoundEvent> cir) {
    if (ReWindConfig.sounds.traditionalZombieVillagerSound) {
      cir.setReturnValue(SoundEvents.ENTITY_ZOMBIE_STEP);
    }
  }
}
