package com.koyomiji.rewind.mixin.client.gui.inventory;

import com.koyomiji.rewind.config.ReWindConfig;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiCrafting.class)
public class MixinCrafting {
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
}
