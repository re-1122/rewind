package com.koyomiji.rewind.mixin.entity;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class MixinEntity {
  @Redirect(method = "move(Lnet/minecraft/entity/MoverType;DDD)V",
            at = @At(value = "FIELD",
                     target = "Lnet/minecraft/entity/Entity;stepHeight:F",
                     ordinal = 0))
  private float
  mixin(Entity self) {
    return getStepHeight(self);
  }

  @Redirect(method = "move(Lnet/minecraft/entity/MoverType;DDD)V",
            at = @At(value = "FIELD",
                     target = "Lnet/minecraft/entity/Entity;stepHeight:F",
                     ordinal = 1))
  private float
  mixin2(Entity self) {
    return getStepHeight(self);
  }

  @Redirect(method = "move(Lnet/minecraft/entity/MoverType;DDD)V",
            at = @At(value = "FIELD",
                     target = "Lnet/minecraft/entity/Entity;stepHeight:F",
                     ordinal = 2))
  private float
  mixin3(Entity self) {
    return getStepHeight(self);
  }

  @Unique
  private float getStepHeight(Entity self) {
    if (ReWindConfig.sneakingStepHeight1) {
      return 1;
    }

    return self.stepHeight;
  }
}
