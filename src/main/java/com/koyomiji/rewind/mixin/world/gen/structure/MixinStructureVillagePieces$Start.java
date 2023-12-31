package com.koyomiji.rewind.mixin.world.gen.structure;

import com.koyomiji.rewind.config.ReWindConfig;
import java.util.List;
import java.util.Random;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StructureVillagePieces.Start.class)
public class MixinStructureVillagePieces$Start
    extends StructureVillagePieces.Well {
  @Inject(
      method =
          "<init>(Lnet/minecraft/world/biome/BiomeProvider;ILjava/util/Random;IILjava/util/List;I)V",
      at = @At(value = "RETURN"))
  private void
  mixin(BiomeProvider biomeProviderIn, int p_i2104_2_, Random rand,
        int p_i2104_4_, int p_i2104_5_,
        List<StructureVillagePieces.PieceWeight> p_i2104_6_, int p_i2104_7_,
        CallbackInfo ci) {
    if (ReWindConfig.preventZombieVillage) {
      this.isZombieInfested = false;
    }
  }
}
