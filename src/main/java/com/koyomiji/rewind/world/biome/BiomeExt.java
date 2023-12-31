package com.koyomiji.rewind.world.biome;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Set;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

public class BiomeExt {
  public static final Set<Biome> EXPLORATION_BIOMES_LIST = Sets.newHashSet();

  static {
    Collections.addAll(EXPLORATION_BIOMES_LIST,
                       new Biome[] {Biomes.OCEAN,
                                    Biomes.PLAINS,
                                    Biomes.DESERT,
                                    Biomes.EXTREME_HILLS,
                                    Biomes.FOREST,
                                    Biomes.TAIGA,
                                    Biomes.SWAMPLAND,
                                    Biomes.RIVER,
                                    Biomes.FROZEN_RIVER,
                                    Biomes.ICE_PLAINS,
                                    Biomes.ICE_MOUNTAINS,
                                    Biomes.MUSHROOM_ISLAND,
                                    Biomes.MUSHROOM_ISLAND_SHORE,
                                    Biomes.BEACH,
                                    Biomes.DESERT_HILLS,
                                    Biomes.FOREST_HILLS,
                                    Biomes.TAIGA_HILLS,
                                    Biomes.JUNGLE,
                                    Biomes.JUNGLE_HILLS,
                                    Biomes.JUNGLE_EDGE,
                                    Biomes.DEEP_OCEAN,
                                    Biomes.STONE_BEACH,
                                    Biomes.COLD_BEACH,
                                    Biomes.BIRCH_FOREST,
                                    Biomes.BIRCH_FOREST_HILLS,
                                    Biomes.ROOFED_FOREST,
                                    Biomes.COLD_TAIGA,
                                    Biomes.COLD_TAIGA_HILLS,
                                    Biomes.REDWOOD_TAIGA,
                                    Biomes.REDWOOD_TAIGA_HILLS,
                                    Biomes.EXTREME_HILLS_WITH_TREES,
                                    Biomes.SAVANNA,
                                    Biomes.SAVANNA_PLATEAU,
                                    Biomes.MESA,
                                    Biomes.MESA_ROCK,
                                    Biomes.MESA_CLEAR_ROCK});
  }
}
