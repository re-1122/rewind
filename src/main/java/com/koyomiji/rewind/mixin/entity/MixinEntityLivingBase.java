package com.koyomiji.rewind.mixin.entity;

import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.util.EnchantmentUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {
  @Shadow protected ItemStack activeItemStack;
  @Shadow protected int activeItemStackUseCount;

  public MixinEntityLivingBase(World worldIn) { super(worldIn); }

  @Redirect(
      method = "knockBack",
      at = @At(value = "FIELD",
               target = "Lnet/minecraft/entity/EntityLivingBase;onGround:Z"))
  public boolean
  mixin(EntityLivingBase entityLivingBase) {
    if (ReWindConfig.oldCombat) {
      return true;
    }

    return entityLivingBase.onGround;
  }

  // https://www.mcpk.wiki/wiki/Momentum_Threshold
  @ModifyConstant(method = "onLivingUpdate",
                  constant = @Constant(doubleValue = 0.003D))
  public double
  mixin(double value) {
    if (ReWindConfig.momentumThreshold0_005) {
      return 0.005D;
    }

    return value;
  }

  @Inject(method = "isActiveItemStackBlocking",
          at = @At(value = "RETURN", ordinal = 1), cancellable = true,
          locals = LocalCapture.CAPTURE_FAILHARD)
  public void
  mixin(CallbackInfoReturnable<Boolean> cir, Item item) {
    if (ReWindConfig.oldCombat) {
      if (item instanceof ItemSword) {
        cir.setReturnValue(false);
      }
    }
  }

  @Shadow
  public IAttributeInstance getEntityAttribute(IAttribute p_110148_1_) {
    return null;
  }

  @Shadow
  public boolean isHandActive() {
    return false;
  }

  @Shadow protected abstract void collideWithNearbyEntities();

  @Shadow public abstract int getTotalArmorValue();

  @Shadow protected abstract void damageArmor(float damage);

  @Inject(method = "playEquipSound", at = @At("HEAD"), cancellable = true)
  protected void playEquipSound(ItemStack stack, CallbackInfo ci) {
    if (ReWindConfig.sounds.noItemEquipSound) {
      ci.cancel();
    }
  }

  @ModifyVariable(method = "onUpdate", at = @At(value = "LOAD"), name = "f2")
  public float mixin2(float value) {
    if (ReWindConfig.rotatePlayerModelWhenMovingBackwards) {
      return 0;
    }

    return value;
  }

  @Redirect(
      method = "onLivingUpdate",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/EntityLivingBase;collideWithNearbyEntities()V")
      )
  public void
  mixin3(EntityLivingBase entityLivingBase) {
    if (ReWindConfig.preventMobPushingPlayer) {
      if (!world.isRemote) {
        collideWithNearbyEntities();
      }

      return;
    }

    collideWithNearbyEntities();
  }

  @Redirect(
      method = "collideWithNearbyEntities",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/EntityLivingBase;getEntityBoundingBox()Lnet/minecraft/util/math/AxisAlignedBB;")
      )
  public AxisAlignedBB
  mixin4(EntityLivingBase entityLivingBase) {
    if (ReWindConfig.preventMobPushingPlayer) {
      return entityLivingBase.getEntityBoundingBox().grow(
          0.20000000298023224D, 0.0D, 0.20000000298023224D);
    }

    return entityLivingBase.getEntityBoundingBox();
  }

  @Redirect(
      method = "applyPotionDamageCalculations",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/enchantment/EnchantmentHelper;getEnchantmentModifierDamage(Ljava/lang/Iterable;Lnet/minecraft/util/DamageSource;)I")
      )
  public int
  mixin5(Iterable<ItemStack> stacks, DamageSource source) {
    if (ReWindConfig.oldCombat) {
      return EnchantmentUtil.getEnchantmentModifierDamageOld(stacks, source);
    }

    return EnchantmentHelper.getEnchantmentModifierDamage(stacks, source);
  }
}
