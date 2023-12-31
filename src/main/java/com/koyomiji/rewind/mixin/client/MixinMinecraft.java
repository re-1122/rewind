package com.koyomiji.rewind.mixin.client;

import com.koyomiji.rewind.client.IMinecraftExt;
import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.debug.GeneralDebugInfo;
import com.koyomiji.rewind.debug.IGeneralDebugInfoProvider;
import com.koyomiji.rewind.network.CPacketClientStatusHelper;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft
    implements IGeneralDebugInfoProvider, IMinecraftExt {
  @Shadow private static int debugFPS;
  @Shadow public PlayerControllerMP playerController;
  @Shadow public EntityPlayerSP player;
  @Shadow public RayTraceResult objectMouseOver;
  @Shadow public WorldClient world;
  @Shadow public EntityRenderer entityRenderer;

  @Unique public GeneralDebugInfo generalDebugInfo;
  @Unique public GuiAchievement guiAchievement;

  @Shadow @Nullable public abstract NetHandlerPlayClient getConnection();

  @Inject(method = "init", at = @At("RETURN"))
  private void mixin2(CallbackInfo ci) {
    Minecraft self = (Minecraft)(Object)this;
    this.guiAchievement = new GuiAchievement(self);

    AchievementList.OPEN_INVENTORY.setStatStringFormatter(str -> {
      try {
        return String.format(
            str, self.gameSettings.keyBindInventory.getDisplayName());
      } catch (Exception exception) {
        return "Error: " + exception.getLocalizedMessage();
      }
    });
  }

  @Inject(
      method = "runGameLoop()V",
      at = @At(
          value = "FIELD",
          target = "Lnet/minecraft/client/Minecraft;debug:Ljava/lang/String;"))
  private void
  mixin(CallbackInfo ci) {
    generalDebugInfo =
        new GeneralDebugInfo(debugFPS, RenderChunk.renderChunksUpdated);
  }

  @Inject(
      method = "runGameLoop",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/shader/Framebuffer;unbindFramebuffer()V"))
  private void
  mixin3(CallbackInfo ci) {
    guiAchievement.updateAchievementWindow();
  }

  @Inject(
      method =
          "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/renderer/EntityRenderer;resetData()V")
      )
  private void
  mixin4(CallbackInfo ci) {
    guiAchievement.clearAchievements();
  }

  @Inject(
      method = "processKeyBinds",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/multiplayer/PlayerControllerMP;isRidingHorse()Z")
      )
  private void
  mixin5(CallbackInfo ci) {
    getConnection().sendPacket(new CPacketClientStatus(
        CPacketClientStatusHelper.OPEN_INVENTORY_ACHIEVEMENT));
  }

  @Override
  public GeneralDebugInfo getGeneralDebugInfo() {
    return generalDebugInfo;
  }

  @Redirect(
      method = "rightClickMouse",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/block/state/IBlockState;getMaterial()Lnet/minecraft/block/material/Material;")
      )
  private Material
  mixin8(IBlockState instance) {
    if (ReWindConfig.traditionalItemUseAnimation) {
      return Material.AIR;
    }

    return instance.getMaterial();
  }

  @Inject(
      method = "rightClickMouse",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/util/math/RayTraceResult;getBlockPos()Lnet/minecraft/util/math/BlockPos;")
      ,
      locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
  private void
  mixin7(CallbackInfo ci, EnumHand[] var1, int var2, int var3,
         EnumHand enumhand, ItemStack itemstack) {
    if (ReWindConfig.traditionalItemUseAnimation) {
      BlockPos blockpos = this.objectMouseOver.getBlockPos();

      if (this.world.getBlockState(blockpos).getMaterial() != Material.AIR) {
        int i = itemstack.getCount();
        EnumActionResult enumactionresult =
            this.playerController.processRightClickBlock(
                this.player, this.world, blockpos, this.objectMouseOver.sideHit,
                this.objectMouseOver.hitVec, enumhand);

        if (enumactionresult == EnumActionResult.SUCCESS) {
          this.player.swingArm(enumhand);
          ci.cancel();
          return;
        }

        if (!itemstack.isEmpty() &&
            (itemstack.getCount() != i ||
             this.playerController.isInCreativeMode())) {
          this.entityRenderer.itemRenderer.resetEquippedProgress(enumhand);
        }
      }
    }
  }

  @Redirect(
      method = "<init>",
      at = @At(value = "INVOKE",
               target = "Ljava/util/Locale;setDefault(Ljava/util/Locale;)V"))
  private void
  mixin6(Locale l) {
    if (ReWindConfig.preventSettingDefaultLocale) {
      return;
    }

    Locale.setDefault(l);
  }

  @Override
  public GuiAchievement getGuiAchievement() {
    return guiAchievement;
  }
}
