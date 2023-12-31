package com.koyomiji.rewind.mixin.client.gui.inventory;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiContainerCreative.class)
public class MixinGuiContainerCreative {
  @ModifyConstant(method = "setCurrentCreativeTab",
                  constant = @Constant(intValue = 54, ordinal = 0))
  private int
  mixin(int original) {
    return ReWindConfig.rearrangeInventory ? 9 : original;
  }

  @ModifyConstant(method = "setCurrentCreativeTab",
                  constant = @Constant(intValue = 35, ordinal = 0))
  private int
  mixin2(int original) {
    return ReWindConfig.rearrangeInventory ? 82 : original;
  }

  @ModifyConstant(method = "drawGuiContainerBackgroundLayer",
                  constant = @Constant(intValue = 88))
  private int
  mixin3(int original) {
    return ReWindConfig.rearrangeInventory ? 43 : original;
  }
}
