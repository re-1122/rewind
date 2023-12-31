package com.koyomiji.rewind.mixin.client.model;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModelSkeleton.class)
public class MixinModelSkeleton {
  @Redirect(
      method = "setLivingAnimations",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/client/model/ModelSkeleton;rightArmPose:Lnet/minecraft/client/model/ModelBiped$ArmPose;",
          opcode = Opcodes.PUTFIELD, ordinal = 1))
  private void
  mixin(ModelSkeleton modelSkeleton, ModelSkeleton.ArmPose rightArmPose) {
    if (ReWindConfig.traditionalSkeletonBehavior) {
      return;
    }

    modelSkeleton.rightArmPose = rightArmPose;
  }

  @Redirect(
      method = "setLivingAnimations",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/client/model/ModelSkeleton;leftArmPose:Lnet/minecraft/client/model/ModelBiped$ArmPose;",
          opcode = Opcodes.PUTFIELD, ordinal = 1))
  private void
  mixin2(ModelSkeleton modelSkeleton, ModelSkeleton.ArmPose leftArmPose) {
    if (ReWindConfig.traditionalSkeletonBehavior) {
      return;
    }

    modelSkeleton.leftArmPose = leftArmPose;
  }

  @Redirect(
      method = "setRotationAngles",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/monster/AbstractSkeleton;isSwingingArms()Z")
      )
  private boolean
  mixin3(AbstractSkeleton instance) {
    if (ReWindConfig.traditionalSkeletonBehavior) {
      return true;
    }

    return instance.isSwingingArms();
  }

  @Redirect(method = "setRotationAngles",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"))
  private boolean
  mixin4(ItemStack instance) {
    if (ReWindConfig.traditionalSkeletonBehavior) {
      return true;
    }

    return instance.isEmpty();
  }
}
