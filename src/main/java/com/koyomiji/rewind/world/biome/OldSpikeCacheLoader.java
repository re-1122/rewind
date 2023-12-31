package com.koyomiji.rewind.world.biome;

import com.google.common.cache.CacheLoader;
import com.google.common.collect.*;
import com.koyomiji.rewind.ChunkLocation;
import com.koyomiji.rewind.PartialWorld;
import com.koyomiji.rewind.world.gen.feature.OldEndSpike;
import java.util.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;

public class OldSpikeCacheLoader extends CacheLoader<World, OldEndSpike[]> {
  public OldEndSpike[] load(World world) throws Exception {
    Random random = new Random(world.getSeed());
    // Always generate the same spikes for the same seed
    long seed = random.nextLong() & 65535L;
    random = new Random(seed);

    PartialWorld partialWorld = generateMainIsland(world, 0, 0);
    List<OldEndSpike> spikes = Lists.newArrayList();

    for (Chunk chunk : partialWorld.getSortedChunks()) {
      /*
       * Implemented according to 1.8.9's BiomeEndDecorator#genDecorations
       */
      if (random.nextInt(5) == 0) {
        int x = chunk.x * 16 + random.nextInt(16) + 8;
        int z = chunk.z * 16 + random.nextInt(16) + 8;

        double distanceFromCenter = Math.sqrt(x * x + z * z);

        // Prevent spikes from colliding with the end gateways and the exit
        // portal
        if (Math.abs(distanceFromCenter - 96) > (4 + 1 + 1) &&
            distanceFromCenter > (4 + 1 + 3)) {
          BlockPos top =
              partialWorld.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));

          if (top != null) {
            OldEndSpike generated = generate(partialWorld, random, top);

            if (generated != null) {
              spikes.add(generated);
            }
          }
        }
      }
    }

    return spikes.toArray(new OldEndSpike[0]);
  }

  /*
   * Implemented according to 1.8.9's WorldGenSpikes#generate
   */
  public OldEndSpike generate(PartialWorld world, Random random,
                              BlockPos position) {
    if (!world.isAirBlock(position) ||
        world.getBlockState(position.down()).getBlock() != Blocks.END_STONE) {
      return null;
    }

    int height = random.nextInt(32) + 6;
    int radius = random.nextInt(4) + 1;

    for (BlockPos.MutableBlockPos pos : BlockPos.getAllInBoxMutable(
             new BlockPos(position.getX() - radius, position.getY() - 1,
                          position.getZ() - radius),
             new BlockPos(position.getX() + radius, position.getY() - 1,
                          position.getZ() + radius))) {
      int x = pos.getX() - position.getX();
      int z = pos.getZ() - position.getZ();

      if (x * x + z * z < radius * radius + 1 &&
          world.getBlockState(pos).getBlock() != Blocks.END_STONE) {
        return null;
      }
    }

    OldEndSpike spike = new OldEndSpike(position.getX(), position.getZ(),
                                        radius, height, position.getY());
    return spike;
  }

  private static PartialWorld generateMainIsland(World world, int originX,
                                                 int originZ) {
    PartialWorld pWorld = new PartialWorld(
        ((ChunkProviderServer)world.getChunkProvider()).chunkGenerator);

    Set<ChunkLocation> visited = Sets.newHashSet();
    List<ChunkLocation> chunks = Lists.newLinkedList();
    chunks.add(new ChunkLocation(originX, originZ));

    while (chunks.size() > 0) {
      ChunkLocation chunk = chunks.remove(0);

      if (visited.contains(chunk)) {
        continue;
      }

      if ((chunk.x * chunk.x + chunk.z * chunk.z) > 4096) {
        continue;
      }

      visited.add(new ChunkLocation(chunk.x, chunk.z));

      for (int x = -1; x <= 1; x++) {
        for (int z = -1; z <= 1; z++) {
          if (Math.abs(x) + Math.abs(z) != 1) {
            continue;
          }

          ChunkLocation id = new ChunkLocation(chunk.x + x, chunk.z + z);

          pWorld.generateChunk(id);

          boolean chunkEmpty = isChunkEmpty(pWorld.getChunk(id));

          if (!chunkEmpty) {
            chunks.add(id);
          }
        }
      }
    }

    return pWorld;
  }

  private static boolean isChunkEmpty(Chunk chunk) {
    return chunk.getLowestHeight() == Integer.MAX_VALUE;
  }
}
