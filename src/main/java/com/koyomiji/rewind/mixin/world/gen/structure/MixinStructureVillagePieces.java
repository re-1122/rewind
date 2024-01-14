package com.koyomiji.rewind.mixin.world.gen.structure;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Random;

@Mixin(StructureVillagePieces.class)
public class MixinStructureVillagePieces {
    @Inject(method = "generateAndAddComponent", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void mixin(StructureVillagePieces.Start start, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType, CallbackInfoReturnable<StructureComponent> cir, StructureComponent structurecomponent) {
        if (ReWindConfig.biomeBoundedVillages) {
            int x = (structurecomponent.getBoundingBox().minX +
                    structurecomponent.getBoundingBox().maxX) /
                    2;
            int y = (structurecomponent.getBoundingBox().minZ +
                    structurecomponent.getBoundingBox().maxZ) /
                    2;
            int rangeX = structurecomponent.getBoundingBox().maxX -
                    structurecomponent.getBoundingBox().minX;
            int rangeY = structurecomponent.getBoundingBox().maxZ -
                    structurecomponent.getBoundingBox().minZ;
            int radius = Math.max(rangeX, rangeY);

            if (start.biomeProvider.areBiomesViable(
                    x, y, radius / 2 + 4, MapGenVillage.VILLAGE_SPAWN_BIOMES)) {
                structureComponents.add(structurecomponent);
                start.pendingHouses.add(structurecomponent);
                cir.setReturnValue(structurecomponent);
            } else {
                cir.setReturnValue(null);
            }
        }
    }

    @Inject(method = "generateAndAddRoadPiece", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void mixin2(StructureVillagePieces.Start start, List<StructureComponent> p_176069_1_, Random rand, int p_176069_3_, int p_176069_4_, int p_176069_5_, EnumFacing facing, int p_176069_7_, CallbackInfoReturnable<StructureComponent> cir, StructureBoundingBox structureboundingbox, StructureComponent structurecomponent) {
        if (ReWindConfig.biomeBoundedVillages) {
            int x = (structurecomponent.getBoundingBox().minX +
                    structurecomponent.getBoundingBox().maxX) /
                    2;
            int y = (structurecomponent.getBoundingBox().minZ +
                    structurecomponent.getBoundingBox().maxZ) /
                    2;
            int rangeX = structurecomponent.getBoundingBox().maxX -
                    structurecomponent.getBoundingBox().minX;
            int rangeY = structurecomponent.getBoundingBox().maxZ -
                    structurecomponent.getBoundingBox().minZ;
            int radius = Math.max(rangeX, rangeY);

            if (start.biomeProvider.areBiomesViable(
                    x, y, radius / 2 + 4, MapGenVillage.VILLAGE_SPAWN_BIOMES)) {
                p_176069_1_.add(structurecomponent);
                start.pendingRoads.add(structurecomponent);
                cir.setReturnValue(structurecomponent);
            } else {
                cir.setReturnValue(null);
            }
        }
    }
}
