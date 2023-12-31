package com.koyomiji.rewind.debug;

public class RenderDispatcherDebugInfo {
  boolean singleThreaded;
  int queueChunkUpdates;
  int queueChunkUploads;
  int queueFreeRenderBuilders;

  public RenderDispatcherDebugInfo(boolean singleThreaded,
                                   int queueChunkUpdates, int queueChunkUploads,
                                   int queueFreeRenderBuilders) {
    this.singleThreaded = singleThreaded;
    this.queueChunkUpdates = queueChunkUpdates;
    this.queueChunkUploads = queueChunkUploads;
    this.queueFreeRenderBuilders = queueFreeRenderBuilders;
  }
}