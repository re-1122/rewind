package com.koyomiji.rewind.debug;

import static org.lwjgl.opengl.GL11.*;

import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.Display;

public class DebugProfile1_8 extends AbstractDebugProfile {
  protected Minecraft mc;
  private static final String VERSION = "1.12.2";

  public DebugProfile1_8(Minecraft mc) { this.mc = mc; }

  protected String getRenderDebugInfoString(RenderDebugInfo info) {
    return String.format(
        "C: %d/%d %sD: %d, L: %d, %s", info.renderedChunks, info.renderChunks,
        info.renderChunksMany ? "(s) " : "", info.renderDistance,
        info.lightUpdates,
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

  protected String getDebugInfoEntitiesString(EntityDebugInfo info) {
    return "E: " + info.entitiesRendered + "/" + info.entitiesTotal +
        ", B: " + info.entitiesHidden + ", I: " +
        (info.entitiesTotal - info.entitiesHidden - info.entitiesRendered);
  }

  protected String getLocalDifficultyString(float additionalDifficulty,
                                            float clampedAdditionalDifficulty,
                                            long time) {
    return String.format("Local Difficulty: %.2f (Day %d)",
                         additionalDifficulty, time / 24000L);
  }

  @Override
  public List<String> getLeftPrimary() {
    String renderDebugInfoString = getRenderDebugInfoString(
        ((IRenderDebugInfoProvider)mc.renderGlobal).getRenderDebugInfo());
    String entityDebugInfoString = getDebugInfoEntitiesString(
        ((IEntityDebugInfoProvider)mc.renderGlobal).getEntityDebugInfo());

    BlockPos pos =
        new BlockPos(this.mc.getRenderViewEntity().posX,
                     this.mc.getRenderViewEntity().getEntityBoundingBox().minY,
                     this.mc.getRenderViewEntity().posZ);

    if (this.mc.isReducedDebug()) {
      return Lists.newArrayList(
          "Minecraft " + VERSION + " (" + this.mc.getVersion() + "/" +
              ClientBrandRetriever.getClientModName() + ")",
          this.mc.debug, renderDebugInfoString, entityDebugInfoString,
          "P: " + this.mc.effectRenderer.getStatistics() +
              ". T: " + this.mc.world.getDebugLoadedEntities(),
          this.mc.world.getProviderName(), "",
          String.format("Chunk-relative: %d %d %d", pos.getX() & 15,
                        pos.getY() & 15, pos.getZ() & 15));
    } else {
      Entity renderView = this.mc.getRenderViewEntity();
      EnumFacing facing = renderView.getHorizontalFacing();
      String toward = "Invalid";

      switch (facing) {
      case NORTH:
        toward = "Towards negative Z";
        break;
      case SOUTH:
        toward = "Towards positive Z";
        break;
      case WEST:
        toward = "Towards negative X";
        break;
      case EAST:
        toward = "Towards positive X";
      }

      List<String> list = Lists.newArrayList(
          "Minecraft " + VERSION + " (" + this.mc.getVersion() + "/" +
              ClientBrandRetriever.getClientModName() +
              ("release".equalsIgnoreCase(this.mc.getVersionType())
                   ? ""
                   : "/" + this.mc.getVersionType()) +
              ")",
          this.mc.debug, renderDebugInfoString, entityDebugInfoString,
          "P: " + this.mc.effectRenderer.getStatistics() +
              ". T: " + this.mc.world.getDebugLoadedEntities(),
          this.mc.world.getProviderName(), "",
          String.format(
              "XYZ: %.3f / %.5f / %.3f", this.mc.getRenderViewEntity().posX,
              this.mc.getRenderViewEntity().getEntityBoundingBox().minY,
              this.mc.getRenderViewEntity().posZ),
          String.format("Block: %d %d %d", pos.getX(), pos.getY(), pos.getZ()),
          String.format("Chunk: %d %d %d in %d %d %d", pos.getX() & 15,
                        pos.getY() & 15, pos.getZ() & 15, pos.getX() >> 4,
                        pos.getY() >> 4, pos.getZ() >> 4),
          String.format("Facing: %s (%s) (%.1f / %.1f)", facing, toward,
                        MathHelper.wrapDegrees(renderView.rotationYaw),
                        MathHelper.wrapDegrees(renderView.rotationPitch)));

      if (this.mc.world != null) {
        Chunk chunk = this.mc.world.getChunk(pos);

        if (this.mc.world.isBlockLoaded(pos) && pos.getY() >= 0 &&
            pos.getY() < 256) {
          if (!chunk.isEmpty()) {
            list.add("Biome: " +
                     chunk.getBiome(pos, this.mc.world.getBiomeProvider())
                         .getBiomeName());
            list.add("Light: " + chunk.getLightSubtracted(pos, 0) + " (" +
                     chunk.getLightFor(EnumSkyBlock.SKY, pos) + " sky, " +
                     chunk.getLightFor(EnumSkyBlock.BLOCK, pos) + " block)");
            DifficultyInstance difficulty =
                this.mc.world.getDifficultyForLocation(pos);

            if (this.mc.isIntegratedServerRunning() &&
                this.mc.getIntegratedServer() != null) {
              EntityPlayerMP player =
                  this.mc.getIntegratedServer().getPlayerList().getPlayerByUUID(
                      this.mc.player.getUniqueID());

              if (player != null) {
                difficulty =
                    player.world.getDifficultyForLocation(new BlockPos(player));
              }
            }

            list.add(getLocalDifficultyString(
                difficulty.getAdditionalDifficulty(),
                difficulty.getClampedAdditionalDifficulty(),
                this.mc.world.getWorldTime()));
          } else {
            list.add("Waiting for chunk...");
          }
        } else {
          list.add("Outside of world...");
        }
      }

      if (this.mc.entityRenderer != null &&
          this.mc.entityRenderer.isShaderActive()) {
        list.add("Shader: " +
                 this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
      }

      if (this.mc.objectMouseOver != null &&
          this.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK &&
          this.mc.objectMouseOver.getBlockPos() != null) {
        BlockPos targetPos = this.mc.objectMouseOver.getBlockPos();
        list.add(String.format("Looking at: %d %d %d", targetPos.getX(),
                               targetPos.getY(), targetPos.getZ()));
      }

      return list;
    }
  }

  @Override
  public List<String> getLeftSecondary() {
    return Lists.newArrayList();
  }

  @Override
  public List<String> getRight() {
    return this.getRightActual();
  }

  public <T extends Comparable<T>> List<String> getRightActual() {
    long maxMem = Runtime.getRuntime().maxMemory();
    long totalMem = Runtime.getRuntime().totalMemory();
    long freeMem = Runtime.getRuntime().freeMemory();
    long usedMem = totalMem - freeMem;

    List<String> list = Lists.newArrayList(
        String.format("Java: %s %dbit", System.getProperty("java.version"),
                      this.mc.isJava64bit() ? 64 : 32),
        String.format("Mem: % 2d%% %03d/%03dMB", usedMem * 100L / maxMem,
                      bytesToMBytes(usedMem), bytesToMBytes(maxMem)),
        String.format("Allocated: % 2d%% %03dMB", totalMem * 100L / maxMem,
                      bytesToMBytes(totalMem)),
        "", String.format("CPU: %s", OpenGlHelper.getCpu()), "",
        String.format("Display: %dx%d (%s)", Display.getWidth(),
                      Display.getHeight(),
                      GlStateManager.glGetString(GL_VENDOR)),
        GlStateManager.glGetString(GL_RENDERER),
        GlStateManager.glGetString(GL_VERSION));

    if (this.mc.isReducedDebug()) {
      return list;
    } else {
      if (this.mc.objectMouseOver != null &&
          this.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK &&
          this.mc.objectMouseOver.getBlockPos() != null) {
        BlockPos targetPos = this.mc.objectMouseOver.getBlockPos();
        IBlockState targetBlock = this.mc.world.getBlockState(targetPos);

        if (this.mc.world.getWorldType() != WorldType.DEBUG_ALL_BLOCK_STATES) {
          targetBlock = targetBlock.getActualState(this.mc.world, targetPos);
        }

        list.add("");
        list.add(String.valueOf(
            Block.REGISTRY.getNameForObject(targetBlock.getBlock())));

        for (UnmodifiableIterator it =
                 targetBlock.getProperties().entrySet().iterator();
             it.hasNext();) {
          Map.Entry<IProperty<?>, Comparable<?>> entry = (Map.Entry)it.next();
          IProperty<T> property = (IProperty)entry.getKey();
          T value = (T)entry.getValue();
          String propertyString = property.getName(value);

          if (Boolean.TRUE.equals(value)) {
            propertyString = TextFormatting.GREEN + propertyString;
          } else if (Boolean.FALSE.equals(value)) {
            propertyString = TextFormatting.RED + propertyString;
          }

          list.add(property.getName() + ": " + propertyString);
        }
      }

      return list;
    }
  }
}