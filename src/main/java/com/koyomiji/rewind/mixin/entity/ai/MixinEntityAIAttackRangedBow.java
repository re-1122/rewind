package com.koyomiji.rewind.mixin.entity.ai;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityAIAttackRangedBow.class)
public abstract class MixinEntityAIAttackRangedBow<
    T extends EntityMob & IRangedAttackMob> extends EntityAIBase {
  @Shadow protected abstract boolean isBowInMainhand();

  private EntityAIAttackRanged entityAIAttackRanged;

  @Inject(method = "<init>", at = @At("RETURN"))
  private void mixin0(EntityMob mob, double moveSpeedAmpIn,
                      int attackCooldownIn, float maxAttackDistanceIn,
                      CallbackInfo ci) {
    entityAIAttackRanged =
        new EntityAIAttackRanged((IRangedAttackMob)mob, moveSpeedAmpIn,
                                 attackCooldownIn, 60, maxAttackDistanceIn);
  }

  @Inject(method = "shouldExecute", at = @At("HEAD"), cancellable = true)
  private void mixin1(CallbackInfoReturnable<Boolean> cir) {
    if (ReWindConfig.traditionalSkeletonBehavior) {
      cir.setReturnValue(entityAIAttackRanged.shouldExecute() &&
                         this.isBowInMainhand());
    }
  }

  @Inject(method = "shouldContinueExecuting", at = @At("HEAD"),
          cancellable = true)
  private void
  mixin2(CallbackInfoReturnable<Boolean> cir) {
    if (ReWindConfig.traditionalSkeletonBehavior) {
      cir.setReturnValue(entityAIAttackRanged.shouldContinueExecuting() &&
                         this.isBowInMainhand());
    }
  }

  @Inject(method = "startExecuting", at = @At("TAIL"))
  private void mixin5(CallbackInfo ci) {
    if (ReWindConfig.traditionalSkeletonBehavior) {
      entityAIAttackRanged.startExecuting();
    }
  }

  @Inject(method = "resetTask", at = @At("TAIL"))
  private void mixin3(CallbackInfo ci) {
    if (ReWindConfig.traditionalSkeletonBehavior) {
      entityAIAttackRanged.resetTask();
    }
  }

  @Inject(method = "updateTask", at = @At("HEAD"), cancellable = true)
  private void mixin4(CallbackInfo ci) {
    if (ReWindConfig.traditionalSkeletonBehavior) {
      entityAIAttackRanged.updateTask();
      ci.cancel();
    }
  }
}
