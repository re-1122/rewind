package com.koyomiji.rewind.debug;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;

public class DebugProfile1_7 extends AbstractDebugProfile {
  private Minecraft mc;
  private static final String VERSION = "1.12.2";

  public DebugProfile1_7(Minecraft mc) { this.mc = mc; }

  protected String getRenderDebugInfoString(RenderDebugInfo info) {
    return String.format(
        "C: %d/%d. D: %d, L: %d, %s", info.renderedChunks, info.renderChunks,
        info.renderDistance, info.lightUpdates,
        info.renderDispatcher == null
            ? "null"
            : getRenderDispatchDebugInfoString(info.renderDispatcher));
  }

  protected String
  getRenderDispatchDebugInfoString(RenderDispatcherDebugInfo info) {
    return info.singleThreaded
        ? String.format("pC: %03d, single-threaded", info.queueChunkUpdates)
        : String.format("pC: %03d, pU: %1d, aB: %1d", info.queueChunkUpdates,
                        info.queueChunkUploads, info.queueFreeRenderBuilders);
  }

  protected String getDebugInfoEntities(EntityDebugInfo info) {
    return "E: " + info.entitiesRendered + "/" + info.entitiesTotal +
        ". B: " + info.entitiesHidden + ", I: " +
        (info.entitiesTotal - info.entitiesHidden - info.entitiesRendered);
  }

  @Override
  public List<String> getLeftPrimary() {
    GeneralDebugInfo info =
        ((IGeneralDebugInfoProvider)mc).getGeneralDebugInfo();
    String renderDebugInfoString = getRenderDebugInfoString(
        ((IRenderDebugInfoProvider)mc.renderGlobal).getRenderDebugInfo());
    String entityDebugInfoString = getDebugInfoEntities(
        ((IEntityDebugInfoProvider)mc.renderGlobal).getEntityDebugInfo());
    String debug = info.fps + " fps, " + info.chunkUpdates + " chunk updates";

    return Lists.newArrayList(
        "Minecraft " + VERSION + " (" + debug + ")", renderDebugInfoString,
        entityDebugInfoString,
        "P: " + this.mc.effectRenderer.getStatistics() +
            ". T: " + this.mc.world.getDebugLoadedEntities(),
        this.mc.world.getProviderName());
  }

  @Override
  public List<String> getLeftSecondary() {
    Entity entity = this.mc.getRenderViewEntity();
    BlockPos blockpos = new BlockPos(
        entity.posX, entity.getEntityBoundingBox().minY, entity.posZ);
    EnumFacing enumfacing = this.mc.player.getHorizontalFacing();

    List<String> list = Lists.newArrayList(
        String.format("x: %.5f (%d) // c: %d (%d)", entity.posX,
                      blockpos.getX(), blockpos.getX() >> 4,
                      blockpos.getX() & 0xF),
        String.format("y: %.3f (feet pos, %.3f eyes pos)",
                      entity.getEntityBoundingBox().minY,
                      entity.posY + (double)entity.getEyeHeight()),
        String.format("z: %.5f (%d) // c: %d (%d)", entity.posZ,
                      blockpos.getZ(), blockpos.getZ() >> 4,
                      blockpos.getZ() & 0xF),
        String.format("f: " + enumfacing.getHorizontalIndex() + " (" +
                      enumfacing.getName().toUpperCase() + ") / " +
                      MathHelper.wrapDegrees(entity.rotationYaw)));

    if (this.mc.world != null) {
      Chunk chunk = this.mc.world.getChunk(blockpos);

      if (this.mc.world.isBlockLoaded(blockpos) && blockpos.getY() >= 0 &&
          blockpos.getY() < 256) {
        if (!chunk.isEmpty()) {
          list.add("lc: " + (chunk.getTopFilledSegment() + 15) + " b: " +
                   chunk.getBiome(blockpos, this.mc.world.getBiomeProvider())
                       .getBiomeName() +
                   " bl: " + chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos) +
                   " sl: " + chunk.getLightFor(EnumSkyBlock.SKY, blockpos) +
                   " rl: " + chunk.getLightSubtracted(blockpos, 0));

          String line6 = "";

          if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            line6 += String.format("ws: %.3f, fs: %.3f, ",
                                   player.capabilities.getWalkSpeed(),
                                   player.capabilities.getFlySpeed());
          }

          line6 += String.format(
              "g: %b, fl: %d", entity.onGround,
              this.mc.world.getHeight(blockpos.getX(), blockpos.getZ()));
          list.add(line6);
        }
      }
    }

    if (this.mc.entityRenderer != null &&
        this.mc.entityRenderer.isShaderActive()) {
      list.add(String.format(
          "shader: %s",
          this.mc.entityRenderer.getShaderGroup().getShaderGroupName()));
    }

    return list;
  }

  @Override
  public List<String> getRight() {
    long maxMem = Runtime.getRuntime().maxMemory();
    long totalMem = Runtime.getRuntime().totalMemory();
    long freeMem = Runtime.getRuntime().freeMemory();
    long usedMem = totalMem - freeMem;

    return Lists.newArrayList(
        "Used memory: " + (usedMem * 100L / maxMem) + "% (" +
            bytesToMBytes(usedMem) + "MB) of " + bytesToMBytes(maxMem) + "MB",
        "Allocated memory: " + (totalMem * 100L / maxMem) + "% (" +
            bytesToMBytes(totalMem) + "MB)");
  }
}