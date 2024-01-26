package com.koyomiji.rewind.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;

public class PartialWorld {
  private IChunkGenerator chunkGenerator;
  private Map<ChunkLocation, Chunk> chunks;

  public PartialWorld(IChunkGenerator chunkGenerator) {
    this.chunkGenerator = chunkGenerator;
    this.chunks = new HashMap<>();
  }

  public IChunkGenerator getChunkGenerator() { return chunkGenerator; }

  public List<Chunk> getSortedChunks() {
    List<Chunk> chunkList = new ArrayList<>(chunks.values());

    chunkList.sort((chunk1, chunk2) -> {
      if (chunk1.x == chunk2.x) {
        return chunk1.z - chunk2.z;
      }

      return chunk1.x - chunk2.x;
    });

    return chunkList;
  }

  public Chunk generateChunk(ChunkLocation location) {
    return generateChunk(location.x, location.z);
  }

  public Chunk generateChunk(int x, int z) {
    if (isChunkGeneratedAt(x, z)) {
      return getChunk(x, z);
    }

    ChunkLocation location = new ChunkLocation(x, z);
    Chunk chunk = chunkGenerator.generateChunk(x, z);
    chunks.put(location, chunk);
    return chunk;
  }

  public boolean isWithinWorld(BlockPos pos) {
    return getBlockState(pos) != null;
  }

  public Chunk getChunk(int x, int z) {
    ChunkLocation location = new ChunkLocation(x, z);
    return chunks.get(location);
  }

  public Chunk getChunk(ChunkLocation location) { return chunks.get(location); }

  public Chunk getChunk(BlockPos pos) {
    return getChunk(pos.getX() >> 4, pos.getZ() >> 4);
  }

  public void setChunk(int x, int z, Chunk chunk) {
    ChunkLocation location = new ChunkLocation(x, z);
    chunks.put(location, chunk);
  }

  public void setChunk(ChunkLocation location, Chunk chunk) {
    chunks.put(location, chunk);
  }

  public void removeChunk(int x, int z) {
    ChunkLocation location = new ChunkLocation(x, z);
    chunks.remove(location);
  }

  public boolean isChunkGeneratedAt(int x, int z) {
    ChunkLocation location = new ChunkLocation(x, z);
    return chunks.containsKey(location);
  }

  public IBlockState getBlockState(BlockPos pos) {
    Chunk chunk = getChunk(pos);
    if (chunk == null) {
      return null;
    }

    return chunk.getBlockState(pos);
  }

  public BlockPos getTopSolidOrLiquidBlock(BlockPos pos) {
    Chunk chunk = getChunk(pos);

    if (chunk == null) {
      return null;
    }

    BlockPos blockpos =
        new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ());

    for (BlockPos blockpos2; blockpos.getY() >= 0; blockpos = blockpos2) {
      blockpos2 = blockpos.down();
      IBlockState iblockstate = chunk.getBlockState(blockpos2);

      if (iblockstate.getMaterial().blocksMovement() &&
          !iblockstate.getBlock().isLeaves(iblockstate, null, blockpos2) &&
          !iblockstate.getBlock().isFoliage(null, blockpos2)) {
        break;
      }
    }

    return blockpos;
  }

  public boolean isAirBlock(BlockPos pos) {
    IBlockState state = getBlockState(pos);
    return state != null && state.getBlock().isAir(state, null, pos);
  }
}
