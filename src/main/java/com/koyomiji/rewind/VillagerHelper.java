package com.koyomiji.rewind;

import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class VillagerHelper {
  public static boolean
  shouldKeepVillager(VillagerRegistry.VillagerProfession prof) {
    return !shouldRemoveVillager(prof);
  }

  public static boolean
  shouldRemoveVillager(VillagerRegistry.VillagerProfession prof) {
    if (ReWindConfig.preventSpawningNitwits) {
      return prof.getRegistryName().toString().equals("minecraft:nitwit");
    }

    return false;
  }
}
