package com.koyomiji.rewind.mixin.server;

import com.koyomiji.rewind.server.IMinecraftServerExt;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer implements IMinecraftServerExt {
  public boolean isAnnouncingPlayerAchievements() { return true; }
}
