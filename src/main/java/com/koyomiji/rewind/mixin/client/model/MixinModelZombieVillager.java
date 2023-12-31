package com.koyomiji.rewind.mixin.client.model;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ModelZombieVillager.class)
public class MixinModelZombieVillager extends ModelBiped {
  @Inject(method = "<init>(FFZ)V", at = @At(value = "RETURN"), require = 1)
  private void mixin(float p_i1165_1_, float p_i1165_2_, boolean p_i1165_3_,
                     CallbackInfo ci) {
    if (ReWindConfig.traditionalZombieVillager) {
      // Restore the default
      float modelSize = p_i1165_1_;
      float p_i1149_2_ = 0.0F;
      int textureWidthIn = 64;
      int textureHeightIn = p_i1165_3_ ? 32 : 64;

      this.leftArmPose = ModelBiped.ArmPose.EMPTY;
      this.rightArmPose = ModelBiped.ArmPose.EMPTY;
      this.textureWidth = textureWidthIn;
      this.textureHeight = textureHeightIn;
      this.bipedHead = new ModelRenderer(this, 0, 0);
      this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
      this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
      this.bipedHeadwear = new ModelRenderer(this, 32, 0);
      this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize + 0.5F);
      this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
      this.bipedBody = new ModelRenderer(this, 16, 16);
      this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
      this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
      this.bipedRightArm = new ModelRenderer(this, 40, 16);
      this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
      this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i1149_2_, 0.0F);
      this.bipedLeftArm = new ModelRenderer(this, 40, 16);
      this.bipedLeftArm.mirror = true;
      this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
      this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i1149_2_, 0.0F);
      this.bipedRightLeg = new ModelRenderer(this, 0, 16);
      this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
      this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + p_i1149_2_, 0.0F);
      this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
      this.bipedLeftLeg.mirror = true;
      this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
      this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + p_i1149_2_, 0.0F);

      // Zombie villager model
      if (p_i1165_3_) {
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8, 6, 8, p_i1165_1_);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1165_2_, 0.0F);
      } else {
        this.bipedHead = new ModelRenderer(this);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1165_2_, 0.0F);
        this.bipedHead.setTextureOffset(0, 32).addBox(-4.0F, -10.0F, -4.0F, 8,
                                                      10, 8, p_i1165_1_);
        this.bipedHead.setTextureOffset(24, 32).addBox(-1.0F, -3.0F, -6.0F, 2,
                                                       4, 2, p_i1165_1_);
      }
    }
  }

  @Inject(method = "setRotationAngles", at = @At(value = "RETURN"), require = 1,
          locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixin(float limbSwing, float limbSwingAmount, float ageInTicks,
        float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn,
        CallbackInfo ci, EntityZombie entityzombie, float f, float f1,
        float f2) {
    if (ReWindConfig.traditionalZombieBehavior) {
      this.bipedRightArm.rotateAngleX = -(float)Math.PI / 2;
      this.bipedLeftArm.rotateAngleX = -(float)Math.PI / 2;
      this.bipedRightArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
      this.bipedLeftArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
    }
  }
}
