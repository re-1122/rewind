package com.koyomiji.rewind.mixin.inventory;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ContainerPlayer.class)
public abstract class MixinContainerPlayer extends Container {
  @ModifyConstant(method = "<init>", constant = @Constant(intValue = 154))
  private int mixin(int original) {
    return ReWindConfig.rearrangeInventory ? 144 : original;
  }

  @ModifyConstant(method = "<init>", constant = @Constant(intValue = 98))
  private int mixin2(int original) {
    return ReWindConfig.rearrangeInventory ? 88 : original;
  }

  @ModifyConstant(method = "<init>", constant = @Constant(intValue = 77))
  private int mixin3(int original) {
    return ReWindConfig.rearrangeInventory ? 80 : original;
  }
}
