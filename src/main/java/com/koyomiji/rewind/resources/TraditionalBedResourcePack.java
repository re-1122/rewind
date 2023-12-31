package com.koyomiji.rewind.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.koyomiji.refound.resources.ResourcePackModifier;
import com.koyomiji.refound.resources.ResourcePackUtil;
import com.koyomiji.rewind.ReWind;
import com.koyomiji.rewind.TextureEditor;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class TraditionalBedResourcePack extends ResourcePackModifier {
  private static final Set<String> beds =
      Sets.newHashSet("minecraft:textures/entity/bed/black.png",
                      "minecraft:textures/entity/bed/blue.png",
                      "minecraft:textures/entity/bed/brown.png",
                      "minecraft:textures/entity/bed/cyan.png",
                      "minecraft:textures/entity/bed/gray.png",
                      "minecraft:textures/entity/bed/green.png",
                      "minecraft:textures/entity/bed/light_blue.png",
                      "minecraft:textures/entity/bed/lime.png",
                      "minecraft:textures/entity/bed/magenta.png",
                      "minecraft:textures/entity/bed/orange.png",
                      "minecraft:textures/entity/bed/pink.png",
                      "minecraft:textures/entity/bed/purple.png",
                      "minecraft:textures/entity/bed/red.png",
                      "minecraft:textures/entity/bed/silver.png",
                      "minecraft:textures/entity/bed/white.png",
                      "minecraft:textures/entity/bed/yellow.png");

  @Override
  public InputStream modifyResource(ResourceLocation location,
                                    InputStream inputStream)
      throws IOException {
    BufferedImage image = ImageIO.read(inputStream);
    BufferedImage edited = TextureEditor.flipImage(
        image, 6, 6, 16, 16, TextureEditor.Orientation.HORIZONTAL);
    edited = TextureEditor.flipImage(edited, 6, 28, 16, 16,
                                     TextureEditor.Orientation.HORIZONTAL);
    return ResourcePackUtil.toPNGByteArrayInputStream(edited);
  }

  @Override
  public boolean resourceExists(ResourceLocation location) {
    return beds.contains(location.toString());
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
    return "TraditionalBedResourcePack";
  }
}
