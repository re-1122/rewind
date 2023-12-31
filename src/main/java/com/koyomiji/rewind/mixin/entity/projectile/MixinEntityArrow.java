package com.koyomiji.rewind.mixin.entity.projectile;

import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.entity.ISidedShootable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityArrow.class)
public abstract class MixinEntityArrow
    extends Entity implements IProjectile, ISidedShootable {
  @Shadow
  public abstract void shoot(Entity shooter, float pitch, float yaw,
                             float p_184547_4_, float velocity,
                             float inaccuracy);

  public MixinEntityArrow(World worldIn) { super(worldIn); }

  // Inertia when player is moving
  @Redirect(
      method = "shoot(Lnet/minecraft/entity/Entity;FFFFF)V",
      at =
          @At(value = "FIELD",
              target = "Lnet/minecraft/entity/projectile/EntityArrow;motionX:D",
              opcode = Opcodes.PUTFIELD),
      require = 1)
  private void
  mixin(EntityArrow arrow, double motionX) {
    if (ReWindConfig.oldCombat) {
      return;
    }

    arrow.motionX = motionX;
  }

  @Redirect(
      method = "shoot(Lnet/minecraft/entity/Entity;FFFFF)V",
      at =
          @At(value = "FIELD",
              target = "Lnet/minecraft/entity/projectile/EntityArrow;motionY:D",
              opcode = Opcodes.PUTFIELD),
      require = 1)
  private void
  mixin2(EntityArrow arrow, double motionY) {
    if (ReWindConfig.oldCombat) {
      return;
    }

    arrow.motionY = motionY;
  }

  @Redirect(
      method = "shoot(Lnet/minecraft/entity/Entity;FFFFF)V",
      at =
          @At(value = "FIELD",
              target = "Lnet/minecraft/entity/projectile/EntityArrow;motionZ:D",
              opcode = Opcodes.PUTFIELD),
      require = 1)
  private void
  mixin3(EntityArrow arrow, double motionZ) {
    if (ReWindConfig.oldCombat) {
      return;
    }

    arrow.motionZ = motionZ;
  }

  public void shoot(Entity entityThrower, float rotationPitchIn,
                    float rotationYawIn, float pitchOffset, float velocity,
                    float inaccuracy, EnumHandSide hand) {
    if (ReWindConfig.oldCombat) {
      float coeff = hand == EnumHandSide.RIGHT ? 1 : -1;

      this.posX -=
          coeff * (MathHelper.cos(rotationYawIn / 180.0F * 3.1415927F) * 0.16F);
      this.posZ -=
          coeff * (MathHelper.sin(rotationYawIn / 180.0F * 3.1415927F) * 0.16F);
      setPosition(this.posX, this.posY, this.posZ);
    }

    shoot(entityThrower, rotationPitchIn, rotationYawIn, pitchOffset, velocity,
          inaccuracy);
  }
}
