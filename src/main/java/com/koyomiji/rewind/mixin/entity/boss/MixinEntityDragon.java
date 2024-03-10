package com.koyomiji.rewind.mixin.entity.boss;

import com.google.common.collect.Lists;
import com.koyomiji.rewind.config.ReWindConfig;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.dragon.phase.IPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseList;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
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
    @Shadow
    public MultiPartEntityPart dragonPartHead;

    @Shadow
    protected abstract boolean attackDragonFrom(DamageSource source,
                                                float amount);

    public double targetX;
    public double targetY;
    public double targetZ;
    public boolean forceNewTarget;
    private Entity target;

    public MixinEntityDragon(World worldIn) {
        super(worldIn);
    }

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

            float yawRadian = this.rotationYaw * ((float) Math.PI) / 180;
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

    /*
     * Implemented according to 1.8.9's EntityDragon#onLivingUpdate
     */
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

            return;
        }

        instance.doLocalUpdate();
    }

    @Redirect(
            method = "onLivingUpdate",
            at = @At(
                    value = "INVOKE",
                    target =
                            "Lnet/minecraft/entity/boss/dragon/phase/IPhase;getTargetLocation()Lnet/minecraft/util/math/Vec3d;")
    )
    private Vec3d mixin1(IPhase instance) {
        if (ReWindConfig.traditionalEnderDragonBehavior) {
            return new Vec3d(this.targetX, this.targetY, this.targetZ);
        }

        return instance.getTargetLocation();
    }

    /*
     * Implemented according to 1.8.9's EntityDragon#onLivingUpdate
     */
    @Redirect(
            method = "onLivingUpdate",
            at = @At(
                    value = "INVOKE",
                    target =
                            "Lnet/minecraft/entity/boss/dragon/phase/IPhase;getMaxRiseOrFall()F")
    )
    private float mixin6(IPhase instance) {
        if (ReWindConfig.traditionalEnderDragonBehavior) {
            return 0.6F;
        }

        return instance.getMaxRiseOrFall();
    }

    /*
     * Implemented according to 1.8.9's EntityDragon#onLivingUpdate
     */
    @Redirect(
            method = "onLivingUpdate",
            at = @At(
                    value = "INVOKE",
                    target =
                            "Lnet/minecraft/entity/boss/dragon/phase/IPhase;getYawFactor()F")
    )
    private float mixin7(IPhase instance) {
        if (ReWindConfig.traditionalEnderDragonBehavior) {
            float motionPlus1 = MathHelper.sqrt(this.motionX * this.motionX +
                    this.motionZ * this.motionZ) +
                    1.0F;
            double motionPlus1Clamped =
                    Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) +
                            1.0;
            motionPlus1Clamped = Math.min(motionPlus1Clamped, 40.0D);

            return (float) (0.7F / motionPlus1Clamped / motionPlus1);
        }

        return instance.getYawFactor();
    }

    @Redirect(
            method = "getHeadYOffset",
            at = @At(
                    value = "INVOKE",
                    target =
                            "Lnet/minecraft/entity/boss/dragon/phase/IPhase;getIsStationary()Z")
    )
    private boolean mixin8(IPhase instance) {
        if (ReWindConfig.traditionalEnderDragonBehavior) {
            return false;
        }

        return instance.getIsStationary();
    }

    @Redirect(
            method = "getHeadPartYOffset",
            at = @At(
                    value = "INVOKE",
                    target =
                            "Lnet/minecraft/entity/boss/dragon/phase/IPhase;getType()Lnet/minecraft/entity/boss/dragon/phase/PhaseList;")
    )
    private PhaseList mixin9(IPhase instance) {
        if (ReWindConfig.traditionalEnderDragonBehavior) {
            return PhaseList.HOLDING_PATTERN; // getIsStationary() == false
        }

        return instance.getType();
    }

    @Redirect(
            method = "onCrystalDestroyed",
            at = @At(
                    value = "INVOKE",
                    target =
                            "Lnet/minecraft/entity/boss/dragon/phase/IPhase;onCrystalDestroyed(Lnet/minecraft/entity/item/EntityEnderCrystal;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/DamageSource;Lnet/minecraft/entity/player/EntityPlayer;)V")
    )
    private void mixin10(IPhase instance, EntityEnderCrystal crystal, BlockPos pos, DamageSource source, EntityPlayer player) {
        if (ReWindConfig.traditionalEnderDragonBehavior) {
            return;
        }

        instance.onCrystalDestroyed(crystal, pos, source, player);
    }
}
