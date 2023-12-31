package com.koyomiji.rewind.mixin.entity.ai;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityAIAttackMelee.class)
public class MixinEntityAIAttackMelee {
  @Redirect(
      method = "checkAndPerformAttack",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/EntityCreature;swingArm(Lnet/minecraft/util/EnumHand;)V")
      )
  private void
  mixin(EntityCreature instance, EnumHand enumHand) {
    if (ReWindConfig.traditionalZombieBehavior) {
      if (!instance.getHeldItem(enumHand).isEmpty()) {
        instance.swingArm(enumHand);
      }

      return;
    }

    instance.swingArm(enumHand);
  }
}
