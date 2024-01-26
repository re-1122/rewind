package com.koyomiji.rewind.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.koyomiji.rewind.ReWind;
import com.koyomiji.rewind.util.PathUtil;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class NoBlockRotateResourcePack implements IResourcePack {
  private static Set<String> blockstates = Sets.newHashSet(
      "minecraft:blockstates/black_concrete_powder.json",
      "minecraft:blockstates/blue_concrete_powder.json",
      "minecraft:blockstates/brown_concrete_powder.json",
      "minecraft:blockstates/cyan_concrete_powder.json",
      "minecraft:blockstates/dirt.json", "minecraft:blockstates/grass.json",
      "minecraft:blockstates/gray_concrete_powder.json",
      "minecraft:blockstates/green_concrete_powder.json",
      "minecraft:blockstates/light_blue_concrete_powder.json",
      "minecraft:blockstates/lime_concrete_powder.json",
      "minecraft:blockstates/magenta_concrete_powder.json",
      "minecraft:blockstates/netherrack.json",
      "minecraft:blockstates/orange_concrete_powder.json",
      "minecraft:blockstates/pink_concrete_powder.json",
      "minecraft:blockstates/purple_concrete_powder.json",
      "minecraft:blockstates/red_concrete_powder.json",
      "minecraft:blockstates/red_sand.json", "minecraft:blockstates/sand.json",
      "minecraft:blockstates/silver_concrete_powder.json",
      "minecraft:blockstates/stone.json",
      "minecraft:blockstates/white_concrete_powder.json",
      "minecraft:blockstates/yellow_concrete_powder.json");

  @Override
  public InputStream getInputStream(ResourceLocation location)
      throws IOException {
    return ReWind.getInputStream(
        PathUtil.concatUnix("assets/rewind/", location.getPath()));
  }

  @Override
  public boolean resourceExists(ResourceLocation location) {
    return blockstates.contains(location.toString());
  }

  @Override
  public Set<String> getResourceDomains() {
    return ImmutableSet.of("minecraft");
  }

  @Nullable
  @Override
  public <T extends IMetadataSection>
      T getPackMetadata(MetadataSerializer metadataSerializer,
                        String metadataSectionName) throws IOException {
    return null;
  }

  @Override
  public BufferedImage getPackImage() throws IOException {
    return null;
  }

  @Override
  public String getPackName() {
    return "NoBlockRotateResourcePack";
  }
}
