package com.koyomiji.rewind.mixin.forge.fml.common.registry;

import com.koyomiji.rewind.util.FilteredRegistryNamaspaced;
import com.koyomiji.rewind.util.VillagerUtil;
import java.util.Random;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = VillagerRegistry.class, remap = false)
public class MixinVillagerRegistry {
  @Redirect(
      method =
          "setRandomProfession(Lnet/minecraft/entity/passive/EntityVillager;Ljava/util/Random;)V",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/util/registry/RegistryNamespaced;getRandomObject(Ljava/util/Random;)Ljava/lang/Object;")
      )
  private static Object
  mixin(RegistryNamespaced<ResourceLocation,
                           VillagerRegistry.VillagerProfession> instance,
        Random random) {
    return new FilteredRegistryNamaspaced<>(instance,
                                            VillagerUtil::shouldKeepVillager)
        .getRandomObject(random);
  }

  @Redirect(
      method =
          "setRandomProfession(Lnet/minecraft/entity/monster/EntityZombieVillager;Ljava/util/Random;)V",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/util/registry/RegistryNamespaced;getRandomObject(Ljava/util/Random;)Ljava/lang/Object;")
      )
  private static Object
  mixin2(RegistryNamespaced<ResourceLocation,
                            VillagerRegistry.VillagerProfession> instance,
         Random random) {
    return new FilteredRegistryNamaspaced<>(instance,
                                            VillagerUtil::shouldKeepVillager)
        .getRandomObject(random);
  }
}
