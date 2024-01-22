package com.koyomiji.rewind.mixin.world.gen.structure;

import com.koyomiji.rewind.config.ReWindConfig;
import java.util.List;
import java.util.Random;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StructureVillagePieces.Start.class)
public class MixinStructureVillagePieces$Start
    extends StructureVillagePieces.Well {
  @Redirect(
      method =
          "<init>(Lnet/minecraft/world/biome/BiomeProvider;ILjava/util/Random;IILjava/util/List;I)V",
      at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
  private int
  mixin(Random random, int bound) {
    if (ReWindConfig.preventZombieVillage) {
      return 1;
    }

    return random.nextInt(bound);
  }

  @ModifyConstant(
      method =
          "<init>(Lnet/minecraft/world/biome/BiomeProvider;ILjava/util/Random;IILjava/util/List;I)V",
      constant = @Constant(intValue = 2))
  private int
  mixin2(int constant) {
    if (ReWindConfig.useOakPlanksInAcaciaVillages) {
      return 0;
    }

    return constant;
  }
}
