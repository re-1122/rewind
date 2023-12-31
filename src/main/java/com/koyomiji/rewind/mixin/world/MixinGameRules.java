package com.koyomiji.rewind.mixin.world;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRules.class)
public class MixinGameRules {
  @ModifyConstant(method = "<init>()V",
                  constant = @Constant(stringValue = "24", ordinal = 0))
  private String
  mixin(String original) {
    return ReWindConfig.disableEntityCrammingByDefault ? "0" : original;
  }
}