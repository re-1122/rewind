package com.koyomiji.rewind.mixin.world.gen.structure;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureVillagePieces.Village.class)
public class MixinStructureVillagePieces$Village {
  @Redirect(
      method = "getAverageGroundLevel",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/WorldProvider;getAverageGroundLevel()I"))
  private int
  mixin(WorldProvider instance) {
    if (ReWindConfig.biomeBoundedVillages) {
      return instance.getAverageGroundLevel() + 1;
    }

    return instance.getAverageGroundLevel();
  }
}
