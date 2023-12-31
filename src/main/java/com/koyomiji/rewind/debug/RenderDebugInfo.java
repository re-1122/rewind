package com.koyomiji.rewind.debug;

public class RenderDebugInfo {
  int renderedChunks;
  int renderChunks;
  boolean renderChunksMany;
  int renderDistance;
  int lightUpdates;
  RenderDispatcherDebugInfo renderDispatcher;

  public RenderDebugInfo(int renderedChunks, int renderChunks,
                         boolean renderChunksMany, int renderDistance,
                         int lightUpdates,
                         RenderDispatcherDebugInfo renderDispatcher) {
    this.renderedChunks = renderedChunks;
    this.renderChunks = renderChunks;
    this.renderChunksMany = renderChunksMany;
    this.renderDistance = renderDistance;
    this.lightUpdates = lightUpdates;
    this.renderDispatcher = renderDispatcher;
  }
}
