package com.koyomiji.rewind.mixin.world;

import com.koyomiji.rewind.ReWind;
import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldServer.class)
public class MixinWorldServer {
  @Redirect(
      method = "updateBlocks",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/GameRules;getBoolean(Ljava/lang/String;)Z"))
  private boolean
  mixin(GameRules instance, String name) {
    if (ReWindConfig.preventSkeletonHorseTrap) {
      return false;
    }

    return instance.getBoolean(name);
  }
}
