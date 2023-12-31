package com.koyomiji.rewind.mixin.client.gui;

import com.koyomiji.refound.config.ReFoundConfig;
import com.koyomiji.rewind.client.renderer.IEntityRendererExt;
import com.koyomiji.rewind.config.ReWindConfig;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiOptions.class)
public class MixinGuiOptions extends GuiScreen {
  @Inject(method = "initGui", at = @At("RETURN"))
  private void mixin(CallbackInfo ci) {
    if (ReWindConfig.addSuperSecretSettings) {
      buttonList.add(new GuiButton(8675309, width / 2 + 5, height / 6 + 144 - 6,
                                   150, 20, "Super Secret Settings...") {
        @Override
        public void playPressSound(
            net.minecraft.client.audio.SoundHandler soundHandlerIn) {
          // TODO: Button sound
        }
      });
    }
  }

  @Inject(method = "actionPerformed", at = @At("RETURN"))
  private void mixin(GuiButton button, CallbackInfo ci) {
    if (button.id == 8675309) {
      ((IEntityRendererExt)mc.entityRenderer).activateNextShader();
    }
  }

  @Redirect(method = "initGui",
            at = @At(value = "INVOKE",
                     target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                     ordinal = 4))
  private boolean
  mixin(List instance, Object e) {
    if (ReWindConfig.removeRealmsNotificationsOption) {
      return false;
    }

    return instance.add(e);
  }
}
