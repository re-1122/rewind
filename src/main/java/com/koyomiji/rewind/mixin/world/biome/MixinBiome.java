package com.koyomiji.rewind.mixin.world.biome;

import com.koyomiji.rewind.biome.IBiomeNameProvider;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Biome.class)
public class MixinBiome implements IBiomeNameProvider {
  @Shadow @Final private String biomeName;

  @Override
  public String rewind$getBiomeName() {
    return biomeName;
  }
}
