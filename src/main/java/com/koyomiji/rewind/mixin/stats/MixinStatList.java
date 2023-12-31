package com.koyomiji.rewind.mixin.stats;

import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatList.class)
public class MixinStatList {
  @Inject(method = "reinit", at = @At(value = "HEAD"), remap = false)
  private static void mixin1(CallbackInfo ci) {
    if (AchievementList.ACHIEVEMENTS != null) {
      AchievementList.ACHIEVEMENTS.clear();
    }
  }
}
