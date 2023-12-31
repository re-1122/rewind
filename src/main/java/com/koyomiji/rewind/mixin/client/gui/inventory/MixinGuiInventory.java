package com.koyomiji.rewind.mixin.client.gui.inventory;

import com.koyomiji.rewind.config.ReWindConfig;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(GuiInventory.class)
public abstract class MixinGuiInventory
    extends InventoryEffectRenderer implements IRecipeShownListener {
  @Shadow @Final private GuiRecipeBook recipeBookGui;

  public MixinGuiInventory(Container inventorySlotsIn) {
    super(inventorySlotsIn);
  }

  @Redirect(method = "initGui",
            at = @At(value = "INVOKE",
                     target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
  private boolean
  mixin(List list, Object obj) {
    if (ReWindConfig.disableRecipeBook) {
      return true;
    }

    return list.add(obj);
  }

  @Unique
  private static final ResourceLocation INVENTORY_BACKGROUND_ARRANGED =
      new ResourceLocation("rewind", "textures/gui/container/inventory.png");

  @Redirect(
      method = "drawGuiContainerBackgroundLayer(FII)V",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V")
      )
  private void
  mixin2(net.minecraft.client.renderer.texture.TextureManager textureManager,
         net.minecraft.util.ResourceLocation resourceLocation) {
    if (ReWindConfig.rearrangeInventory) {
      textureManager.bindTexture(INVENTORY_BACKGROUND_ARRANGED);
      return;
    }

    textureManager.bindTexture(INVENTORY_BACKGROUND);
  }

  @ModifyConstant(method = "drawGuiContainerForegroundLayer",
                  constant = @Constant(intValue = 97))
  private int
  mixin3(int original) {
    return ReWindConfig.rearrangeInventory ? 86 : original;
  }

  @Redirect(
      method = "drawScreen",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/client/gui/inventory/GuiInventory;hasActivePotionEffects:Z")
      )
  private void
  mixin4(GuiInventory instance, boolean value) {
    if (ReWindConfig.shiftInventoryWhenHavingEffects) {
      return;
    }

    hasActivePotionEffects = value;
  }

  @Redirect(
      method = "initGui",
      at = @At(
          value = "FIELD",
          target = "Lnet/minecraft/client/gui/inventory/GuiInventory;guiLeft:I",
          opcode = Opcodes.PUTFIELD))
  private void
  mixin5(GuiInventory instance, int recipeGuiLeft) {
    if (ReWindConfig.shiftInventoryWhenHavingEffects) {
      if (recipeBookGui.isVisible()) {
        this.guiLeft = recipeGuiLeft;
      } else {
        updateActivePotionEffects();
      }

      return;
    }

    this.guiLeft = recipeGuiLeft;
  }

  @Redirect(
      method = "actionPerformed",
      at = @At(
          value = "FIELD",
          target = "Lnet/minecraft/client/gui/inventory/GuiInventory;guiLeft:I",
          opcode = Opcodes.PUTFIELD))
  private void
  mixin6(GuiInventory instance, int recipeGuiLeft) {
    if (ReWindConfig.shiftInventoryWhenHavingEffects) {
      if (recipeBookGui.isVisible()) {
        this.guiLeft = recipeGuiLeft;
      } else {
        updateActivePotionEffects();
      }

      return;
    }

    this.guiLeft = recipeGuiLeft;
  }
}
