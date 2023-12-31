package com.koyomiji.rewind.mixin.item;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemHoe.class)
public class MixinItemHoe {
  @Inject(method = "hitEntity", at = @At("HEAD"), cancellable = true)
  public void mixin(ItemStack stack, EntityLivingBase target,
                    EntityLivingBase attacker,
                    CallbackInfoReturnable<Boolean> cir) {
    if (ReWindConfig.preventHoeDamageWhenAttack) {
      cir.setReturnValue(false);
    }
  }

  /**
   * @author Komichi
   * @reason Implement traditional hoe sound
   */
  @Overwrite
  protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn,
                          BlockPos pos, IBlockState state) {
    if (ReWindConfig.sounds.traditionalHoeSound) {
      SoundType soundType = state.getBlock().getSoundType();
      worldIn.playSound(
          player, pos, soundType.getStepSound(), SoundCategory.BLOCKS,
          (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
    } else {
      worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    if (!worldIn.isRemote) {
      worldIn.setBlockState(pos, state, 11);
      stack.damageItem(1, player);
    }
  }
}
