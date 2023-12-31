package com.koyomiji.rewind.mixin.block;

import com.koyomiji.rewind.config.ReWindConfig;
import java.util.Random;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockFalling.class)
public class MixinBlockFallingClient {
  @Inject(method = "randomDisplayTick", at = @At("HEAD"), cancellable = true)
  public void randomDisplayTick(IBlockState stateIn, World worldIn,
                                BlockPos pos, Random rand, CallbackInfo ci) {
    if (ReWindConfig.preventFallingBlockParticle) {
      ci.cancel();
    }
  }
}
