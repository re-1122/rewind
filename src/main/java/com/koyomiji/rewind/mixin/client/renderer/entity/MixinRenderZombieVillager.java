package com.koyomiji.rewind.mixin.client.renderer.entity;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.client.renderer.entity.RenderZombieVillager;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderZombieVillager.class)
public class MixinRenderZombieVillager {
  private static final ResourceLocation ZOMBIE_VILLAGER_TEXTURES =
      new ResourceLocation("textures/entity/zombie/zombie_villager.png");

  @Inject(
      method =
          "getEntityTexture(Lnet/minecraft/entity/monster/EntityZombieVillager;)Lnet/minecraft/util/ResourceLocation;",
      at = @At("HEAD"), cancellable = true)
  protected void
  getEntityTexture(EntityZombieVillager entity,
                   CallbackInfoReturnable<ResourceLocation> cir) {
    if (ReWindConfig.traditionalZombieVillager) {
      cir.setReturnValue(ZOMBIE_VILLAGER_TEXTURES);
    }
  }
}
