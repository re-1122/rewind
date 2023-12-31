package com.koyomiji.rewind.mixin.client.settings;

import com.koyomiji.rewind.client.settings.IGameSettingsExt;
import com.koyomiji.rewind.config.ReWindConfig;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameSettings.class)
public class MixinGameSettings implements IGameSettingsExt {
  @Unique public boolean showInventoryAchievementHint = true;
  @Shadow public boolean autoJump;

  @Inject(method = "<init>()V", at = @At(value = "RETURN"))
  private void mixin(CallbackInfo ci) {
    if (ReWindConfig.turnOffAutoJumpAutomatically) {
      autoJump = false;
    }
  }

  @Inject(method = "loadOptions", at = @At(value = "RETURN"))
  private void mixin2(CallbackInfo ci) {
    if (ReWindConfig.turnOffAutoJumpAutomatically) {
      autoJump = false;
    }
  }

  @Inject(
      method = "loadOptions",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/nbt/NBTTagCompound;getString(Ljava/lang/String;)Ljava/lang/String;")
      ,
      locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixin3(CallbackInfo ci, FileInputStream fileInputStream, List list,
         NBTTagCompound nbttagcompound, Iterator var4, String s1) {
    String s2 = nbttagcompound.getString(s1);

    if ("showInventoryAchievementHint".equals(s1)) {
      this.showInventoryAchievementHint = "true".equals(s2);
    }
  }

  @Inject(method = "saveOptions",
          at = @At(value = "INVOKE",
                   target = "Ljava/io/PrintWriter;println(Ljava/lang/String;)V",
                   ordinal = 43),
          locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixin4(CallbackInfo ci, PrintWriter printwriter) {
    printwriter.println("showInventoryAchievementHint:" +
                        this.showInventoryAchievementHint);
  }

  @Override
  public void setShowInventoryAchievementHint(boolean newValue) {
    showInventoryAchievementHint = newValue;
  }

  @Override
  public boolean getShowInventoryAchievementHint() {
    return showInventoryAchievementHint;
  }
}
