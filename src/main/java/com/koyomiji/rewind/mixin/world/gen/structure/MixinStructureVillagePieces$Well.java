package com.koyomiji.rewind.mixin.world.gen.structure;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(StructureVillagePieces.Well.class)
public abstract class MixinStructureVillagePieces$Well
    extends StructureVillagePieces.Village {
  @ModifyVariable(method = "addComponentParts",
                  at = @At(value = "LOAD", ordinal = 3), name = "iblockstate")
  private IBlockState
  mixin(IBlockState value) {
    if (ReWindConfig.gravelPathInVillages) {
      return this.getBiomeSpecificBlockState(Blocks.GRAVEL.getDefaultState());
    }

    return value;
  }
}
