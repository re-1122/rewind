package com.koyomiji.rewind.mixin.advancements;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdvancementRewards.class)
public class MixinAdvancementRewards {
  @Inject(method = "apply", at = @At(value = "HEAD"), cancellable = true)
  public void apply(EntityPlayerMP player, CallbackInfo ci) {
    if (ReWindConfig.enableAchievement) {
      ci.cancel();
    }
  }
}
