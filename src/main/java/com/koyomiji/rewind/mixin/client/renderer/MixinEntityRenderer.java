package com.koyomiji.rewind.mixin.client.renderer;

import com.koyomiji.rewind.client.renderer.IEntityRendererExt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer
    implements IEntityRendererExt, IResourceManagerReloadListener {
  @Shadow @Final private Minecraft mc;

  @Shadow private ShaderGroup shaderGroup;

  @Shadow private int shaderIndex;

  @Shadow @Final public static int SHADER_COUNT;

  @Shadow public abstract void loadShader(ResourceLocation resourceLocationIn);

  @Shadow @Final private static ResourceLocation[] SHADERS_TEXTURES;

  @Override
  public void activateNextShader() {
    if (OpenGlHelper.shadersSupported) {
      if (mc.getRenderViewEntity() instanceof EntityPlayer) {
        if (shaderGroup != null) {
          shaderGroup.deleteShaderGroup();
        }

        shaderIndex = (shaderIndex + 1) % (SHADER_COUNT + 1);

        if (shaderIndex != SHADER_COUNT) {
          loadShader(SHADERS_TEXTURES[shaderIndex]);
        } else {
          shaderGroup = null;
        }
      }
    }
  }
}
