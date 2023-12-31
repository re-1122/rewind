package com.koyomiji.rewind.mixin.entity.monster;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityIronGolem.class)
public class MixinEntityIronGolem extends EntityGolem {
  public MixinEntityIronGolem(World worldIn) { super(worldIn); }

  @Redirect(
      method = "applyEntityAttributes",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/entity/ai/attributes/IAttributeInstance;setBaseValue(D)V",
          ordinal = 2))
  private void
  mixin(IAttributeInstance instance, double value) {
    if (ReWindConfig.disableIronGolemKnockbackResistance) {
      return;
    }

    instance.setBaseValue(value);
  }
}
