package com.koyomiji.rewind.mixin.client.renderer;

import com.koyomiji.rewind.SoundRegistry;
import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.debug.*;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal
    implements IRenderDebugInfoProvider, IEntityDebugInfoProvider {
  @Shadow private ViewFrustum viewFrustum;

  @Shadow protected abstract int getRenderedChunks();

  @Shadow @Final private Minecraft mc;

  @Shadow private int renderDistanceChunks;

  @Shadow @Final private Set<BlockPos> setLightUpdates;

  @Shadow private ChunkRenderDispatcher renderDispatcher;

  @Shadow private int countEntitiesRendered;

  @Shadow private int countEntitiesTotal;

  @Shadow private int countEntitiesHidden;

  public RenderDebugInfo getRenderDebugInfo() {
    return new RenderDebugInfo(
        getRenderedChunks(), this.viewFrustum.renderChunks.length,
        this.mc.renderChunksMany, renderDistanceChunks, setLightUpdates.size(),
        renderDispatcher == null
            ? null
            : ((IRenderDispatcherDebugInfoProvider)renderDispatcher)
                  .getRenderDispatcherDebugInfo());
  }

  @Override
  public EntityDebugInfo getEntityDebugInfo() {
    return new EntityDebugInfo(this.countEntitiesRendered,
                               this.countEntitiesTotal,
                               this.countEntitiesHidden);
  }

  @Redirect(
      method = "playEvent",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_WOODEN_DOOR_CLOSE:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin0() {
    if (ReWindConfig.sounds.traditionalDoorSound) {
      return SoundRegistry.BLOCK_DOOR_CLOSE;
    }

    return SoundEvents.BLOCK_WOODEN_DOOR_CLOSE;
  }

  @Redirect(
      method = "playEvent",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_IRON_DOOR_CLOSE:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin1() {
    if (ReWindConfig.sounds.traditionalDoorSound) {
      return SoundRegistry.BLOCK_DOOR_CLOSE;
    }

    return SoundEvents.BLOCK_IRON_DOOR_CLOSE;
  }

  @Redirect(
      method = "playEvent",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_WOODEN_TRAPDOOR_CLOSE:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin2() {
    if (ReWindConfig.sounds.traditionalDoorSound) {
      return SoundRegistry.BLOCK_TRAPDOOR_CLOSE;
    }

    return SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE;
  }

  @Redirect(
      method = "playEvent",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_IRON_TRAPDOOR_CLOSE:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin3() {
    if (ReWindConfig.sounds.traditionalDoorSound) {
      return SoundRegistry.BLOCK_TRAPDOOR_CLOSE;
    }

    return SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE;
  }

  @Redirect(
      method = "playEvent",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_WOODEN_DOOR_OPEN:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin4() {
    if (ReWindConfig.sounds.traditionalDoorSound) {
      return SoundRegistry.BLOCK_DOOR_OPEN;
    }

    return SoundEvents.BLOCK_WOODEN_DOOR_OPEN;
  }

  @Redirect(
      method = "playEvent",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_IRON_DOOR_OPEN:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin5() {
    if (ReWindConfig.sounds.traditionalDoorSound) {
      return SoundRegistry.BLOCK_DOOR_OPEN;
    }

    return SoundEvents.BLOCK_IRON_DOOR_OPEN;
  }

  @Redirect(
      method = "playEvent",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_WOODEN_TRAPDOOR_OPEN:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin6() {
    if (ReWindConfig.sounds.traditionalDoorSound) {
      return SoundRegistry.BLOCK_TRAPDOOR_OPEN;
    }

    return SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN;
  }

  @Redirect(
      method = "playEvent",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_IRON_TRAPDOOR_OPEN:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin7() {
    if (ReWindConfig.sounds.traditionalDoorSound) {
      return SoundRegistry.BLOCK_TRAPDOOR_OPEN;
    }

    return SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN;
  }

  @Redirect(
      method = "playEvent",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_FENCE_GATE_CLOSE:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin8() {
    if (ReWindConfig.sounds.traditionalDoorSound) {
      return SoundRegistry.BLOCK_FENCE_GATE_CLOSE;
    }

    return SoundEvents.BLOCK_FENCE_GATE_CLOSE;
  }

  @Redirect(
      method = "playEvent",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/init/SoundEvents;BLOCK_FENCE_GATE_OPEN:Lnet/minecraft/util/SoundEvent;")
      )
  private SoundEvent
  mixin9() {
    if (ReWindConfig.sounds.traditionalDoorSound) {
      return SoundRegistry.BLOCK_FENCE_GATE_OPEN;
    }

    return SoundEvents.BLOCK_FENCE_GATE_OPEN;
  }
}
