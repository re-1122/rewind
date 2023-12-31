package com.koyomiji.rewind.mixin.client.entity;

import com.koyomiji.rewind.config.ReWindConfig;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP extends AbstractClientPlayer {
  public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
    super(worldIn, playerProfile);
  }

  @ModifyVariable(
      method = "sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
      name = "actionBar", require = 1, at = @At(value = "LOAD"))
  private boolean
  mixin(boolean actionBar) {
    if (ReWindConfig.showOverlayMessageInChat) {
      return false;
    }

    return actionBar;
  }
}
