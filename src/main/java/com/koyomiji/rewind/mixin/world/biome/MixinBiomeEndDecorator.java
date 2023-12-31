package com.koyomiji.rewind.mixin.world.biome;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.world.biome.OldSpikeCacheLoader;
import com.koyomiji.rewind.world.gen.feature.OldEndSpike;
import java.util.concurrent.TimeUnit;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeEndDecorator.class)
public class MixinBiomeEndDecorator extends BiomeDecorator {
  @Unique
  private static final LoadingCache<World, OldEndSpike[]> oldSpikeCache =
      CacheBuilder.newBuilder()
          .expireAfterWrite(5L, TimeUnit.MINUTES)
          .build(new OldSpikeCacheLoader());

  @Inject(method = "getSpikesForWorld", at = @At("HEAD"), cancellable = true)
  private static void
  mixin(World p_185426_0_,
        CallbackInfoReturnable<WorldGenSpikes.EndSpike[]> cir) {
    if (ReWindConfig.classicEndSpikes) {
      cir.setReturnValue(oldSpikeCache.getUnchecked(p_185426_0_));
    }
  }
}
