package com.koyomiji.rewind.mixin.forge.client;

import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.debug.*;
import java.util.ArrayList;
import java.util.HashMap;
import joptsimple.internal.Strings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = GuiIngameForge.class, remap = false)
public class MixinGuiIngameForge extends GuiIngame {
  @Shadow private FontRenderer fontrenderer;
  @Shadow private RenderGameOverlayEvent eventParent;
  @Unique private HashMap<DebugOverlayStyle, IDebugProfile> debugProfileMap;

  public MixinGuiIngameForge(Minecraft mc) { super(mc); }

  //    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;)V", require =
  //    1, at = @At(value = "NEW", target =
  //    "net/minecraftforge/client/GuiIngameForge$GuiOverlayDebugForge"),
  //    cancellable = true)
  @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;)V", require = 1,
          at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixin(Minecraft mc, CallbackInfo ci) {
    debugProfileMap = new HashMap<>();
    debugProfileMap.put(DebugOverlayStyle.VANILLA_1_12,
                        new DebugProfile1_12(mc));
    debugProfileMap.put(DebugOverlayStyle.VANILLA_1_8, new DebugProfile1_8(mc));
    debugProfileMap.put(DebugOverlayStyle.VANILLA_1_7, new DebugProfile1_7(mc));
  }

  @Inject(method = "renderHUDText", at = @At("HEAD"), cancellable = true)
  protected void renderHUDText(int width, int height, CallbackInfo ci) {
    DebugOverlayStyle style = ReWindConfig.debugOverlayStyle;

    if (style != DebugOverlayStyle.DEFAULT) {
      IDebugProfile debugProfile = debugProfileMap.get(style);
      boolean translucent = style == DebugOverlayStyle.VANILLA_1_8 ||
                            style == DebugOverlayStyle.VANILLA_1_12;

      mc.profiler.startSection("forgeHudText");
      OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA,
                               1, 0);
      ArrayList<String> listL = new ArrayList<String>();
      ArrayList<String> listL2 = new ArrayList<String>();
      ArrayList<String> listR = new ArrayList<String>();

      if (mc.isDemo()) {
        long time = mc.world.getTotalWorldTime();
        if (time >= 120500L) {
          listR.add(I18n.format("demo.demoExpired"));
        } else {
          listR.add(I18n.format(
              "demo.remainingTime",
              StringUtils.ticksToElapsedTime((int)(120500L - time))));
        }
      }

      if (this.mc.gameSettings.showDebugInfo &&
          !pre(RenderGameOverlayEvent.ElementType.DEBUG)) {
        listL.addAll(debugProfile.getLeftPrimary());

        if (translucent) {
          listL.addAll(debugProfile.getLeftSecondary());
        } else {
          listL2.addAll(debugProfile.getLeftSecondary());
        }

        listR.addAll(debugProfile.getRight());
        post(RenderGameOverlayEvent.ElementType.DEBUG);
      }

      RenderGameOverlayEvent.Text event =
          new RenderGameOverlayEvent.Text(eventParent, listL, listR);
      if (!MinecraftForge.EVENT_BUS.post(event)) {
        int top = 2;

        for (String msg : listL) {
          if (!translucent) {
            if (!Strings.isNullOrEmpty(msg)) {
              fontrenderer.drawStringWithShadow(msg, 2, top, 16777215);
            }

            top += fontrenderer.FONT_HEIGHT + 1;
          } else {
            if (!Strings.isNullOrEmpty(msg)) {
              drawRect(1, top - 1, 2 + fontrenderer.getStringWidth(msg) + 1,
                       top + fontrenderer.FONT_HEIGHT - 1, -1873784752);
              fontrenderer.drawString(msg, 2, top, 14737632);
            }

            top += fontrenderer.FONT_HEIGHT;
          }
        }

        if (!translucent) {
          top += 12; // 64 - (2 + 10 * 5) = 12

          for (String msg : listL2) {
            if (!Strings.isNullOrEmpty(msg)) {
              fontrenderer.drawStringWithShadow(msg, 2, top, 14737632);
            }

            top += fontrenderer.FONT_HEIGHT - 1;
          }
        }

        top = 2;
        for (String msg : listR) {
          int w = fontrenderer.getStringWidth(msg);
          int left = width - 2 - w;

          if (!translucent) {
            if (!Strings.isNullOrEmpty(msg)) {
              fontrenderer.drawStringWithShadow(msg, left, top, 14737632);
            }

            top += fontrenderer.FONT_HEIGHT + 1;
          } else {
            if (!Strings.isNullOrEmpty(msg)) {
              drawRect(left - 1, top - 1, left + w + 1,
                       top + fontrenderer.FONT_HEIGHT - 1, -1873784752);
              fontrenderer.drawString(msg, left, top, 14737632);
            }

            top += fontrenderer.FONT_HEIGHT;
          }
        }
      }

      mc.profiler.endSection();
      post(RenderGameOverlayEvent.ElementType.TEXT);
      ci.cancel();
    }
  }

  @Shadow
  private boolean pre(RenderGameOverlayEvent.ElementType type) {
    return false;
  }

  @Shadow
  private void post(RenderGameOverlayEvent.ElementType type) {}
}
