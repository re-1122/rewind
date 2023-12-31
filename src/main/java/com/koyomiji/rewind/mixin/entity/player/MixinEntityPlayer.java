package com.koyomiji.rewind.mixin.entity.player;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {
  private BlockPos startMinecartRidingCoordinate;

  public MixinEntityPlayer(World worldIn) { super(worldIn); }

  @Shadow public InventoryPlayer inventory;

  @Shadow public abstract void addStat(StatBase stat, int amount);

  @Shadow public abstract void addStat(StatBase stat);

  @Redirect(
      method = "attackTargetEntityWithCurrentItem",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/entity/player/EntityPlayer;isSprinting()Z",
          ordinal = 1))
  public boolean
  mixin(EntityPlayer player) {
    if (ReWindConfig.oldCombat) {
      return false;
    }

    return player.isSprinting();
  }

  @Redirect(
      method = "attackTargetEntityWithCurrentItem",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/EntityPlayer;DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V")
      )
  public void
  mixin2(World world, EntityPlayer player, double x, double y, double z,
         net.minecraft.util.SoundEvent soundIn,
         net.minecraft.util.SoundCategory category, float volume, float pitch) {
    if (ReWindConfig.oldCombat) {
      return;
    }

    world.playSound(player, x, y, z, soundIn, category, volume, pitch);
  }

  @Redirect(
      method = "attackTargetEntityWithCurrentItem",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/EntityLivingBase;knockBack(Lnet/minecraft/entity/Entity;FDD)V",
          ordinal = 0))
  public void
  mixin3(EntityLivingBase entityLivingBase, Entity entityIn, float strength,
         double xRatio, double zRatio) {
    if (ReWindConfig.oldCombat) {
      entityLivingBase.addVelocity(-xRatio * strength, 0.1D,
                                   -zRatio * strength);
      return;
    }

    entityLivingBase.knockBack(entityIn, strength, xRatio, zRatio);
  }

  @ModifyVariable(method = "attackTargetEntityWithCurrentItem",
                  at = @At(value = "LOAD"), name = "flag3")
  public boolean
  mixin4(boolean flag3) {
    if (ReWindConfig.oldCombat) {
      return false;
    }

    return flag3;
  }

  @Redirect(
      method = "attackTargetEntityWithCurrentItem",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/WorldServer;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V")
      )
  public void
  mixin5(WorldServer instance, EnumParticleTypes particleType, double xCoord,
         double yCoord, double zCoord, int numberOfParticles, double xOffset,
         double yOffset, double zOffset, double particleSpeed,
         int[] particleArguments) {
    if (ReWindConfig.oldCombat) {
      return;
    }

    instance.spawnParticle(particleType, xCoord, yCoord, zCoord,
                           numberOfParticles, xOffset, yOffset, zOffset,
                           particleSpeed, particleArguments);
  }

  public boolean isBlocking() {
    if (this.isHandActive() && !this.activeItemStack.isEmpty()) {
      Item item = this.activeItemStack.getItem();

      return item.getItemUseAction(this.activeItemStack) == EnumAction.BLOCK &&
          item instanceof ItemSword;
    } else {
      return false;
    }
  }

  @Inject(
      method = "damageEntity",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraftforge/common/ISpecialArmor$ArmorProperties;applyArmor(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/util/NonNullList;Lnet/minecraft/util/DamageSource;D)F",
          remap = false),
      locals = LocalCapture.CAPTURE_FAILHARD)
  public void
  damageEntity(DamageSource damageSrc, float damageAmount, CallbackInfo ci) {
    if (ReWindConfig.oldCombat) {
      if (!damageSrc.isUnblockable() && isBlocking() && damageAmount > 0.0F) {
        damageAmount = (1.0F + damageAmount) * 0.5F;
      }
    }
  }

  @Inject(method = "getCooledAttackStrength", at = @At(value = "HEAD"),
          cancellable = true)
  public void
  getCooledAttackStrength(float adjustTicks,
                          CallbackInfoReturnable<Float> cir) {
    if (ReWindConfig.oldCombat) {
      cir.setReturnValue(1.0F);
    }
  }

  @Inject(
      method = "attackTargetEntityWithCurrentItem",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/player/EntityPlayer;setLastAttackedEntity(Lnet/minecraft/entity/Entity;)V")
      ,
      locals = LocalCapture.CAPTURE_FAILHARD)
  public void
  attackTargetEntityWithCurrentItem(Entity targetEntity, CallbackInfo ci,
                                    float f, float f1, float f2, boolean flag,
                                    boolean flag1, int i, boolean flag2,
                                    CriticalHitEvent hitResult, boolean flag3,
                                    double d0, float f4, boolean flag4, int j,
                                    double d1, double d2, double d3) {
    if (f > 18) {
      this.addStat(AchievementList.OVERKILL);
    }
  }

  @Inject(
      method = "onUpdate",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/entity/player/EntityPlayer;updateCape()V"))
  private void
  mixin6(CallbackInfo ci) {
    if (!isRiding()) {
      startMinecartRidingCoordinate = null;
    }
  }

  @Inject(
      method = "addMountedMovementStat",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/player/EntityPlayer;addStat(Lnet/minecraft/stats/StatBase;I)V",
          ordinal = 0))
  private void
  mixin7(double p_71015_1_, double p_71015_3_, double p_71015_5_,
         CallbackInfo ci) {
    if (startMinecartRidingCoordinate == null) {
      startMinecartRidingCoordinate = new BlockPos(this);
    } else if (startMinecartRidingCoordinate.distanceSq(this.posX, this.posY,
                                                        this.posZ) >= 1000000) {
      this.addStat(AchievementList.ON_A_RAIL);
    }
  }

  @Inject(method = "onKillEntity", at = @At(value = "HEAD"))
  private void mixin8(EntityLivingBase entityLivingIn, CallbackInfo ci) {
    if (entityLivingIn instanceof IMob) {
      this.addStat(AchievementList.KILL_ENEMY);
    }
  }

  @ModifyConstant(method = "attackTargetEntityWithCurrentItem",
                  constant = @Constant(floatValue = 0.1F))
  private float
  mixin9(float constant) {
    if (ReWindConfig.traditionalExhaustionValue) {
      return 0.3F;
    }

    return constant;
  }

  @ModifyConstant(method = "jump", constant = @Constant(floatValue = 0.2F))
  private float mixin10(float constant) {
    if (ReWindConfig.traditionalExhaustionValue) {
      return 0.8F;
    }

    return constant;
  }

  @ModifyConstant(method = "jump", constant = @Constant(floatValue = 0.05F))
  private float mixin11(float constant) {
    if (ReWindConfig.traditionalExhaustionValue) {
      return 0.2F;
    }

    return constant;
  }

  @ModifyConstant(method = "addMovementStat",
                  constant = @Constant(floatValue = 0.01F, ordinal = 0))
  private float
  mixin12(float constant) {
    if (ReWindConfig.traditionalExhaustionValue) {
      return 0.015F;
    }

    return constant;
  }

  @ModifyConstant(method = "addMovementStat",
                  constant = @Constant(floatValue = 0.01F, ordinal = 2))
  private float
  mixin13(float constant) {
    if (ReWindConfig.traditionalExhaustionValue) {
      return 0.015F;
    }

    return constant;
  }

  @ModifyConstant(method = "addMovementStat",
                  constant = @Constant(floatValue = 0.0F))
  private float
  mixin14(float constant) {
    if (ReWindConfig.traditionalExhaustionValue) {
      return 0.01F;
    }

    return constant;
  }
}
