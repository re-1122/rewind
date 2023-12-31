package com.koyomiji.rewind.mixin.entity.item;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityBoat.class)
public abstract class MixinEntityBoat extends Entity {
  public MixinEntityBoat(World worldIn) { super(worldIn); }

  @Redirect(
      method = "onUpdate",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/item/EntityBoat;applyEntityCollision(Lnet/minecraft/entity/Entity;)V")
      )
  public void
  mixin(EntityBoat entityBoat, net.minecraft.entity.Entity entity) {
    if (ReWindConfig.preventMobPushingPlayer) {
      if (entity.canBePushed() && entity instanceof EntityBoat) {
        this.applyEntityCollision(entity);
      }

      return;
    }

    this.applyEntityCollision(entity);
  }
}
