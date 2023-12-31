package com.koyomiji.rewind.mixin.client.renderer;

import com.google.common.base.MoreObjects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
  @Inject(
      method =
          "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/renderer/ItemRenderer;transformSideFirstPerson(Lnet/minecraft/util/EnumHandSide;F)V",
          shift = At.Shift.AFTER, ordinal = 2),
      locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixin(AbstractClientPlayer player, float p_187457_2_, float p_187457_3_,
        EnumHand hand, float p_187457_5_, ItemStack stack, float p_187457_7_,
        CallbackInfo ci, boolean flag, EnumHandSide enumhandside, boolean flag1,
        int j) {
    if (stack.getItem() instanceof ItemSword) {
      this.transformFirstPersonSword(enumhandside, p_187457_5_);
      float f8 = 0.4F;
      GlStateManager.scale(f8, f8, f8);

      GlStateManager.translate(j * -0.5F, 0.2F, 0.0F);
      GlStateManager.rotate(j * 30.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(j * 60.0F, 0.0F, 1.0F, 0.0F);

      GlStateManager.scale(1 / f8, 1 / f8, 1 / f8);
      int i = enumhandside == EnumHandSide.RIGHT ? 1 : -1;
      GlStateManager.rotate((float)i * -45.0F, 0.0F, 1.0F, 0.0F);
    }
  }

  private void transformFirstPersonSword(EnumHandSide hand, float p_187453_2_) {
    int i = hand == EnumHandSide.RIGHT ? 1 : -1;
    float f = MathHelper.sin(p_187453_2_ * p_187453_2_ * (float)Math.PI);
    GlStateManager.rotate((float)i * (45.0F + f * -20.0F), 0.0F, 1.0F, 0.0F);
    float f1 = MathHelper.sin(MathHelper.sqrt(p_187453_2_) * (float)Math.PI);
    GlStateManager.rotate((float)i * f1 * -20.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
  }
}
