package com.koyomiji.rewind.mixin.entity.monster;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.entity.monster.EntityCreeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityCreeper.class)
public abstract class MixinEntityCreeper {
    @Shadow protected abstract void spawnLingeringCloud();

    @Redirect(
        method = "explode",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/monster/EntityCreeper;spawnLingeringCloud()V"
        )
    )
    public void mixin(EntityCreeper instance) {
        if (ReWindConfig.noCreeperLingeringCloud) {
            return;
        }

        spawnLingeringCloud();
    }
}
