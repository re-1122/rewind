package com.koyomiji.rewind.mixin.item;

import com.koyomiji.rewind.config.ReWindConfig;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemAxe.class)
public class MixinItemAxe extends ItemTool {
  protected MixinItemAxe(float attackDamageIn, float attackSpeedIn,
                         ToolMaterial materialIn,
                         Set<Block> effectiveBlocksIn) {
    super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
  }

  @Inject(method = "<init>(Lnet/minecraft/item/Item$ToolMaterial;)V",
          at = @At(value = "RETURN"), require = 1)
  private void
  mixin(Item.ToolMaterial material, CallbackInfo ci) {
    if (ReWindConfig.oldCombat) {
      this.attackDamage = 3.0F + material.getAttackDamage();
    }
  }
}
