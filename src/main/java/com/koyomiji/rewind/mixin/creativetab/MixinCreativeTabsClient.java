package com.koyomiji.rewind.mixin.creativetab;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeTabs.class)
public abstract class MixinCreativeTabsClient {
  @Shadow @Final private int index;

  @Shadow public abstract String getBackgroundImageName();

  @Shadow @Final public static CreativeTabs INVENTORY;

  @Inject(method = "getBackgroundImage", at = @At("HEAD"), cancellable = true,
          remap = false)
  private void
  mixin(CallbackInfoReturnable<ResourceLocation> cir) {
    if (index == INVENTORY.getIndex() && ReWindConfig.rearrangeInventory) {
      cir.setReturnValue(new ResourceLocation(
          "rewind", "textures/gui/container/creative_inventory/tab_" +
                        this.getBackgroundImageName()));
    }
  }
}
