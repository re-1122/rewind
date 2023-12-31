package com.koyomiji.rewind.mixin.client.multiplayer;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {
  @Shadow private GameType currentGameType;
  @Shadow private NetHandlerPlayClient connection;

  @Shadow
  private void syncCurrentPlayItem() {}

  @Inject(
      method = "processRightClick",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/util/ActionResult;getType()Lnet/minecraft/util/EnumActionResult;")
      ,
      locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
  private void
  mixin(EntityPlayer player, World worldIn, EnumHand hand,
        CallbackInfoReturnable<EnumActionResult> cir, ItemStack itemstack,
        EnumActionResult cancelResult, int i, ActionResult actionresult,
        ItemStack itemstack1) {
    if (ReWindConfig.traditionalItemUseAnimation) {
      if (itemstack1 != itemstack || itemstack1.getCount() != i) {
        cir.setReturnValue(actionresult.getType());
      } else {
        cir.setReturnValue(EnumActionResult.PASS);
      }
    }
  }
}
