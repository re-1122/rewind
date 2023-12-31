package com.koyomiji.rewind.mixin.client.renderer;

import com.koyomiji.rewind.client.renderer.IRenderItemExt;
import com.koyomiji.rewind.config.ReWindConfig;
import javax.annotation.Nullable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class MixinRenderItem implements IRenderItemExt {
  @Redirect(
      method = "renderItemOverlayIntoGUI",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/renderer/RenderItem;draw(Lnet/minecraft/client/renderer/BufferBuilder;IIIIIIII)V",
          ordinal = 1))
  private void
  mixin3(RenderItem renderItem, BufferBuilder renderer, int x, int y, int width,
         int height, int red, int green, int blue, int alpha) {
    if (ReWindConfig.lessBrighterDurabilityBar) {
      return;
    }

    draw(renderer, x, y, width, height, red, green, blue, alpha);
  }

  @Inject(
      method = "renderItemOverlayIntoGUI",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/renderer/GlStateManager;enableBlend()V",
          ordinal = 1))
  private void
  mixin4(FontRenderer fr, ItemStack stack, int xPosition, int yPosition,
         String text, CallbackInfo ci) {
    if (ReWindConfig.lessBrighterDurabilityBar) {
      double health = stack.getItem().getDurabilityForDisplay(stack);
      BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
      int j = (int)Math.round(13.0D - health * 13.0D);
      int i = (int)Math.round(255.0D - health * 255.0D);
      draw(bufferbuilder, xPosition + 2, yPosition + 13, 12, 1, (255 - i) / 4,
           64, 0, 255);
      draw(bufferbuilder, xPosition + 2, yPosition + 13, j, 1, 255 - i, i, 0,
           255);
    }
  }

  @Shadow
  private void draw(BufferBuilder renderer, int x, int y, int width, int height,
                    int red, int green, int blue, int alpha) {}

  @Shadow private boolean notRenderingEffectsInGUI;

  @Shadow @Final private ItemModelMesher itemModelMesher;

  @Shadow
  public IBakedModel
  getItemModelWithOverrides(ItemStack stack, @Nullable World worldIn,
                            @Nullable EntityLivingBase entitylivingbaseIn) {
    return null;
  }

  @Override
  public void isNotRenderingEffectsInGUI(boolean isNot) {
    notRenderingEffectsInGUI = isNot;
  }

  private IBakedModel
  getItemModelWithoutOverrides(ItemStack stack, @Nullable World worldIn,
                               @Nullable EntityLivingBase entitylivingbaseIn) {
    return this.itemModelMesher.getItemModel(stack);
  }

  @Redirect(
      method =
          "renderItemAndEffectIntoGUI(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;II)V",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/renderer/RenderItem;getItemModelWithOverrides(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/client/renderer/block/model/IBakedModel;")
      )
  private IBakedModel
  mixin5(RenderItem instance, ItemStack stack, World worldIn,
         EntityLivingBase entitylivingbaseIn) {
    if (ReWindConfig.disableItemOverridesInHotBar) {
      return getItemModelWithoutOverrides(stack, worldIn, entitylivingbaseIn);
    }

    return getItemModelWithOverrides(stack, worldIn, entitylivingbaseIn);
  }
}
