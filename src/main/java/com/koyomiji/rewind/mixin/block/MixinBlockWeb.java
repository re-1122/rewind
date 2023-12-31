package com.koyomiji.rewind.mixin.block;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockWeb.class)
public class MixinBlockWeb extends Block {
  public MixinBlockWeb(Material blockMaterialIn, MapColor blockMapColorIn) {
    super(blockMaterialIn, blockMapColorIn);
  }

  @Inject(method = "harvestBlock", at = @At("HEAD"), cancellable = true)
  private void mixin(World worldIn, EntityPlayer player, BlockPos pos,
                     IBlockState state, TileEntity te, ItemStack stack,
                     CallbackInfo ci) {
    if (ReWindConfig.cobwebHarvestableOnlyWithSilkTouch) {
      super.harvestBlock(worldIn, player, pos, state, te, stack);
      ci.cancel();
    }
  }

  @Inject(method = "isShearable", at = @At("HEAD"), cancellable = true,
          remap = false)
  private void
  mixin2(ItemStack item, IBlockAccess world, BlockPos pos,
         CallbackInfoReturnable<Boolean> cir) {
    if (ReWindConfig.cobwebHarvestableOnlyWithSilkTouch) {
      cir.setReturnValue(false);
    }
  }
}
