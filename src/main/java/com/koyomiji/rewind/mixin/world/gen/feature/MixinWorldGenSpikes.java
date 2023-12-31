package com.koyomiji.rewind.mixin.world.gen.feature;

import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.world.gen.feature.OldEndSpike;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WorldGenSpikes.class)
public class MixinWorldGenSpikes {
  @Shadow private WorldGenSpikes.EndSpike spike;

  @ModifyConstant(method = "generate", constant = @Constant(intValue = 0))
  private int mixin(int original) {
    if (ReWindConfig.classicEndSpikes) {
      return ((OldEndSpike)this.spike).getBaseHeight();
    }

    return original;
  }
}
