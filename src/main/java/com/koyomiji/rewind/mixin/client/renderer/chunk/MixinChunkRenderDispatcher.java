package com.koyomiji.rewind.mixin.client.renderer.chunk;

import com.koyomiji.rewind.debug.IRenderDispatcherDebugInfoProvider;
import com.koyomiji.rewind.debug.RenderDispatcherDebugInfo;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChunkRenderDispatcher.class)
public class MixinChunkRenderDispatcher
    implements IRenderDispatcherDebugInfoProvider {
  @Shadow @Final private List<Thread> listWorkerThreads;
  @Shadow
  @Final
  private PriorityBlockingQueue<ChunkCompileTaskGenerator> queueChunkUpdates;
  @Shadow @Final private Queue queueChunkUploads;
  @Shadow
  @Final
  private BlockingQueue<RegionRenderCacheBuilder> queueFreeRenderBuilders;

  @Override
  public RenderDispatcherDebugInfo getRenderDispatcherDebugInfo() {
    return new RenderDispatcherDebugInfo(
        listWorkerThreads.isEmpty(), queueChunkUpdates.size(),
        queueChunkUploads.size(), queueFreeRenderBuilders.size());
  }
}
