package com.koyomiji.rewind.mixin.client.model;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ModelZombie.class)
public class MixinModelZombie extends ModelBiped {
  @Inject(method = "setRotationAngles", at = @At(value = "RETURN"), require = 1,
          locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixin(float limbSwing, float limbSwingAmount, float ageInTicks,
        float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn,
        CallbackInfo ci, boolean flag, float f, float f1, float f2) {
    if (ReWindConfig.traditionalZombieBehavior) {
      this.bipedRightArm.rotateAngleX = -(float)Math.PI / 2;
      this.bipedLeftArm.rotateAngleX = -(float)Math.PI / 2;
      this.bipedRightArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
      this.bipedLeftArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
    }
  }
}
