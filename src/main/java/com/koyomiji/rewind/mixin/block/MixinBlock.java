package com.koyomiji.rewind.mixin.block;

import com.koyomiji.rewind.ReWindSoundType;
import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {
  @Shadow protected SoundType blockSoundType;

  @Inject(method = "setSoundType", at = @At("HEAD"), cancellable = true)
  private void mixin1(SoundType sound, CallbackInfoReturnable<Block> cir) {
    if (ReWindConfig.sounds.traditionalGlassSound) {
      if (sound == SoundType.GLASS) {
        blockSoundType = ReWindSoundType.GLASS;
        cir.setReturnValue((Block)(Object)this);
      }
    }
  }

  @ModifyConstant(method = "harvestBlock",
                  constant = @Constant(floatValue = 0.005F))
  private float
  mixin0(float constant) {
    if (ReWindConfig.traditionalExhaustionValue) {
      return 0.025F;
    }

    return constant;
  }
}
