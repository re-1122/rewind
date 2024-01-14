package com.koyomiji.rewind.mixin.world.gen.feature;

import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.world.gen.feature.OldEndSpike;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldGenSpikes.class)
public abstract class MixinWorldGenSpikes extends WorldGenerator {
  @Shadow private WorldGenSpikes.EndSpike spike;

  @ModifyConstant(method = "generate", constant = @Constant(intValue = 0))
  private int mixin(int original) {
    if (ReWindConfig.classicEndSpikes) {
      return ((OldEndSpike)this.spike).getBaseHeight();
    }

    return original;
  }

  @Redirect(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/WorldGenSpikes;setBlockAndNotifyAdequately(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)V", ordinal = 1))
    private void mixin2(WorldGenSpikes instance, World world, BlockPos blockPos, IBlockState iBlockState) {
        if (ReWindConfig.classicEndSpikes) {
          return;
        }

        setBlockAndNotifyAdequately(world, blockPos, iBlockState);
    }
}
