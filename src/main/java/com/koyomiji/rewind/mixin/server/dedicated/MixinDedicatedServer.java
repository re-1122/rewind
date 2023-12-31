package com.koyomiji.rewind.mixin.server.dedicated;

import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.server.IMinecraftServerExt;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DedicatedServer.class)
public class MixinDedicatedServer implements IMinecraftServerExt {
  @Shadow private PropertyManager settings;

  public boolean isAnnouncingPlayerAchievements() {
    if (ReWindConfig.enableAchievement) {
      return settings.getBooleanProperty("announce-player-achievements", true);
    }

    return false;
  }

  @Redirect(
      method = "init",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/server/dedicated/PropertyManager;hasProperty(Ljava/lang/String;)Z")
      )
  private boolean
  mixin(PropertyManager instance, String key) {
    // Create property if necessary
    isAnnouncingPlayerAchievements();

    return false;
  }
}
