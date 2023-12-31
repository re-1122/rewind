package com.koyomiji.rewind.mixin.world.biome;

import com.koyomiji.rewind.config.ReWindConfig;
import java.util.List;
import net.minecraft.world.biome.BiomeHell;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BiomeHell.class)
public class MixinBiomeHell {
  @Redirect(method = "<init>",
            at = @At(value = "INVOKE",
                     target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                     ordinal = 3))
  private boolean
  add(List list, Object obj) {
    if (ReWindConfig.preventEndermenInNether) {
      return false;
    }

    return list.add(obj);
  }
}
