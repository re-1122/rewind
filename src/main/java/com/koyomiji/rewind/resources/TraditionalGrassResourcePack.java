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

public class TraditionalGrassResourcePack implements IResourcePack {
  private static Set<String> textures =
      Sets.newHashSet("minecraft:textures/blocks/double_plant_grass_bottom.png",
                      "minecraft:textures/blocks/double_plant_grass_top.png",
                      "minecraft:textures/blocks/tallgrass.png");

  @Override
  public InputStream getInputStream(ResourceLocation location)
      throws IOException {
    return ReWind.getInputStream(
        PathUtil.concatUnix("assets/rewind/", location.getPath()));
  }

  @Override
  public boolean resourceExists(ResourceLocation location) {
    return textures.contains(location.toString());
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
    return "TraditionalGrassResourcePack";
  }
}
