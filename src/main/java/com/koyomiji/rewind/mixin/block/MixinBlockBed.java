package com.koyomiji.rewind.mixin.block;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBed.class)
public abstract class MixinBlockBed
    extends BlockHorizontal implements ITileEntityProvider {
  protected MixinBlockBed(Material materialIn) { super(materialIn); }

  @Inject(method = "onFallenUpon", at = @At("HEAD"), cancellable = true)
  public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn,
                           float fallDistance, CallbackInfo ci) {
    if (ReWindConfig.preventBedBounce) {
      super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
      ci.cancel();
    }
  }

  @Inject(method = "onLanded", at = @At("HEAD"), cancellable = true)
  public void onLanded(World worldIn, Entity entityIn, CallbackInfo ci) {
    if (ReWindConfig.preventBedBounce) {
      super.onLanded(worldIn, entityIn);
      ci.cancel();
    }
  }
}