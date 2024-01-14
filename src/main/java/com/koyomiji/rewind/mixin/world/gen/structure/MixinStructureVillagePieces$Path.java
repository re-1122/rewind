package com.koyomiji.rewind.mixin.world.gen.structure;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(StructureVillagePieces.Path.class)
public class MixinStructureVillagePieces$Path {
    @Inject(method = "addComponentParts", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/structure/StructureBoundingBox;isVecInside(Lnet/minecraft/util/math/Vec3i;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void mixin2(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn, CallbackInfoReturnable<Boolean> cir, IBlockState iblockstate, IBlockState iblockstate1, IBlockState iblockstate2, IBlockState iblockstate3, int i, int j, BlockPos blockpos) {
        if (ReWindConfig.gravelPathInVillages) {
            if (structureBoundingBoxIn.isVecInside(blockpos)) {
                blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos).down();
                worldIn.setBlockState(blockpos, iblockstate2, 2);
                worldIn.setBlockState(blockpos.down(), iblockstate3, 2);
            }
        }
    }

    @Redirect(method = "addComponentParts", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/structure/StructureBoundingBox;isVecInside(Lnet/minecraft/util/math/Vec3i;)Z"))
    private boolean mixin(StructureBoundingBox instance, Vec3i blockpos) {
        if (ReWindConfig.gravelPathInVillages) {
            return false;
        }

        return instance.isVecInside(blockpos);
    }
}
