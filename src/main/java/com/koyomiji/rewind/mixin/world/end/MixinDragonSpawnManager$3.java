package com.koyomiji.rewind.mixin.world.end;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.world.end.DragonSpawnManager$3")
public class MixinDragonSpawnManager$3 {
  @Redirect(
      method = "process",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/WorldServer;setBlockToAir(Lnet/minecraft/util/math/BlockPos;)Z")
      )
  private boolean
  mixin(WorldServer instance, BlockPos blockPos) {
    if (ReWindConfig.classicEndSpikes) {
      return false;
    }

    return instance.setBlockToAir(blockPos);
  }
}
