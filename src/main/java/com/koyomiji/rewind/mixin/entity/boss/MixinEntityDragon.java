package com.koyomiji.rewind.mixin.entity.boss;

import com.google.common.collect.Lists;
import com.koyomiji.rewind.config.ReWindConfig;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.dragon.phase.IPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityDragon.class)
public abstract class MixinEntityDragon extends EntityLiving {
  @Shadow public MultiPartEntityPart dragonPartHead;

  @Shadow
  protected abstract boolean attackDragonFrom(DamageSource source,
                                              float amount);

  @Shadow public boolean slowed;
  public double targetX;
  public double targetY;
  public double targetZ;
  public boolean forceNewTarget;
  private Entity target;

  public MixinEntityDragon(World worldIn) { super(worldIn); }

  @Inject(method = "<init>", at = @At("RETURN"))
  private void init(World worldIn, CallbackInfo ci) {
    this.targetY = 100;
  }

  @Redirect(
      method = "collideWithEntities",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/boss/dragon/phase/IPhase;getIsStationary()Z")
      )
  private boolean
  getIsStationary(IPhase instance) {
    if (ReWindConfig.traditionalEnderDragonBehavior) {
      return true;
    }

    return instance.getIsStationary();
  }

  /*
   * Implemented according to 1.8.9's EntityDragon#setNewTarget
   */
  private void setNewTarget() {
    this.forceNewTarget = false;
    List<EntityPlayer> players = Lists.newArrayList(this.world.playerEntities);
    players.removeIf(EntityPlayer::isSpectator);

    if (this.rand.nextInt(2) == 0 && !players.isEmpty()) {
      this.target = players.get(this.rand.nextInt(players.size()));
    } else {
      boolean beyond10;

      do {
        this.targetX = 0;
        this.targetY = (70 + this.rand.nextFloat() * 50);
        this.targetZ = 0;
        this.targetX += (this.rand.nextFloat() * 120 - 60);
        this.targetZ += (this.rand.nextFloat() * 120 - 60);

        double dx = this.posX - this.targetX;
        double dy = this.posY - this.targetY;
        double dz = this.posZ - this.targetZ;
        beyond10 = dx * dx + dy * dy + dz * dz > 100.0D;
      } while (!beyond10);

      this.target = null;
    }
  }

  /*
   * Implemented according to 1.8.9's EntityDragon#attackEntityFromPart
   */
  @Inject(method = "attackEntityFromPart", at = @At("HEAD"), cancellable = true)
  public void attackEntityFromPart(MultiPartEntityPart part,
                                   DamageSource source, float damage,
                                   CallbackInfoReturnable<Boolean> cir) {
    if (ReWindConfig.traditionalEnderDragonBehavior) {
      if (part != this.dragonPartHead) {
        damage = damage / 4.0F + 1.0F;
      }

      float yawRadian = this.rotationYaw * ((float)Math.PI) / 180;
      float sinYaw = MathHelper.sin(yawRadian);
      float cosYaw = MathHelper.cos(yawRadian);
      this.targetX =
          this.posX + (sinYaw * 5) + ((this.rand.nextFloat() - 0.5) * 2);
      this.targetY = this.posY + (this.rand.nextFloat() * 3) + 1;
      this.targetZ =
          this.posZ - (cosYaw * 5) + ((this.rand.nextFloat() - 0.5) * 2);
      this.target = null;

      if (source.getTrueSource() instanceof EntityPlayer ||
          source.isExplosion()) {
        this.attackDragonFrom(source, damage);
      }

      cir.setReturnValue(true);
    }
  }

  @Redirect(
      method = "onLivingUpdate",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/boss/dragon/phase/IPhase;getIsStationary()Z",
          ordinal = 0))
  private boolean
  mixin4(IPhase instance) {
    if (ReWindConfig.traditionalEnderDragonBehavior) {
      return true;
    }

    return instance.getIsStationary();
  }

  @Redirect(
      method = "onLivingUpdate",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/boss/dragon/phase/IPhase;getIsStationary()Z",
          ordinal = 1))
  private boolean
  mixin5(IPhase instance) {
    if (ReWindConfig.traditionalEnderDragonBehavior) {
      return false;
    }

    return instance.getIsStationary();
  }

  @Redirect(
      method = "onLivingUpdate",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/boss/dragon/phase/IPhase;doClientRenderEffects()V")
      )
  private void
  mixin3(IPhase instance) {
    if (ReWindConfig.traditionalEnderDragonBehavior) {
      return;
    }

    instance.doClientRenderEffects();
  }

  @Redirect(
      method = "onLivingUpdate",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/boss/dragon/phase/IPhase;doLocalUpdate()V")
      )
  private void
  mixin2(IPhase instance) {
    if (ReWindConfig.traditionalEnderDragonBehavior) {
      return;
    }

    instance.doLocalUpdate();
  }

  /*
   * Implemented according to 1.8.9's EntityDragon#onLivingUpdate
   */
  @Redirect(
      method = "onLivingUpdate",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/boss/dragon/phase/IPhase;getTargetLocation()Lnet/minecraft/util/math/Vec3d;")
      )
  private Vec3d
  mixin(IPhase instance) {
    if (ReWindConfig.traditionalEnderDragonBehavior) {
      double dx = this.targetX - this.posX;
      double dy = this.targetY - this.posY;
      double dz = this.targetZ - this.posZ;
      double distanceSq = dx * dx + dy * dy + dz * dz;

      if (this.target != null) {
        this.targetX = this.target.posX;
        this.targetZ = this.target.posZ;

        double dx2 = this.targetX - this.posX;
        double dz2 = this.targetZ - this.posZ;
        double horizontalDistance = Math.sqrt(dx2 * dx2 + dz2 * dz2);
        double offsetY = 0.4F + horizontalDistance / 80.0D - 1.0D;
        offsetY = Math.min(offsetY, 10);
        this.targetY = (this.target.getEntityBoundingBox()).minY + offsetY;
      } else {
        this.targetX += this.rand.nextGaussian() * 2;
        this.targetZ += this.rand.nextGaussian() * 2;
      }

      if (this.forceNewTarget || distanceSq < 100 || distanceSq > 22500 ||
          this.collidedHorizontally || this.collidedVertically) {
        this.setNewTarget();
      }

      dy /= MathHelper.sqrt(dx * dx + dz * dz);
      dy = MathHelper.clamp(dy, -0.6f, 0.6f);
      this.motionY += dy * 0.1F;

      this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);

      // An ender dragon's yawRotation is the opposite of its visual. So the
      // dragon's visual facing angle is (180 + rotationYaw)
      // https://bugs.mojang.com/browse/MC-135115
      // -atan2(x, z) is the direction of target from ender dragon
      double targetYaw = 180 - MathHelper.atan2(dx, dz) * 180 / Math.PI;
      double yawDiff = MathHelper.wrapDegrees(targetYaw - this.rotationYaw);
      yawDiff = MathHelper.clamp(yawDiff, -50, 50);

      Vec3d towardTarget =
          (new Vec3d(this.targetX - this.posX, this.targetY - this.posY,
                     this.targetZ - this.posZ))
              .normalize();

      // Vitually facing direction
      Vec3d facing =
          (new Vec3d(
               MathHelper.sin((this.rotationYaw * ((float)Math.PI) / 180)),
               this.motionY,
               -MathHelper.cos(this.rotationYaw * ((float)Math.PI) / 180)))
              .normalize();

      // cosFacingTargetNormalized: [0, 1]
      float cosFacingTargetNormalized =
          ((float)facing.dotProduct(towardTarget) + 0.5f) / 1.5f;
      cosFacingTargetNormalized = Math.max(cosFacingTargetNormalized, 0);

      this.randomYawVelocity *= 0.8F;

      float motionPlus1 = MathHelper.sqrt(this.motionX * this.motionX +
                                          this.motionZ * this.motionZ) +
                          1.0F;
      double motionPlus1Clamped =
          Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) +
          1.0;
      motionPlus1Clamped = Math.min(motionPlus1Clamped, 40.0D);

      this.randomYawVelocity =
          (float)(this.randomYawVelocity +
                  yawDiff * 0.7F / motionPlus1Clamped / motionPlus1);
      this.rotationYaw += this.randomYawVelocity * 0.1;

      // motionInv: [0, 1]
      float motionInv = (float)(2.0 / (motionPlus1Clamped + 1));
      float frictionCoeff = 0.06f;
      moveRelative(0, 0, -1,
                   frictionCoeff *
                       (cosFacingTargetNormalized * motionInv + 1 - motionInv));

      if (this.slowed) {
        this.move(MoverType.SELF, this.motionX * 0.8F, this.motionY * 0.8F,
                  this.motionZ * 0.8F);
      } else {
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      }

      // motionVec: [0, 1]
      Vec3d motionVec =
          (new Vec3d(this.motionX, this.motionY, this.motionZ)).normalize();
      // motionCoeff: [0, 1]
      float motionCoeff = ((float)motionVec.dotProduct(facing) + 1.0F) / 2.0F;
      motionCoeff = 0.8F + 0.15F * motionCoeff;
      this.motionX *= motionCoeff;
      this.motionZ *= motionCoeff;
      this.motionY *= 0.91F;

      return null;
    }

    return instance.getTargetLocation();
  }
}
