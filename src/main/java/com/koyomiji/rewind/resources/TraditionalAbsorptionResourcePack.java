package com.koyomiji.rewind.resources;

import com.google.common.collect.ImmutableSet;
import com.koyomiji.refound.resources.ResourcePackModifier;
import com.koyomiji.refound.resources.ResourcePackUtil;
import com.koyomiji.rewind.ReWind;
import com.koyomiji.rewind.util.TextureEditor;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;

import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class TraditionalAbsorptionResourcePack extends ResourcePackModifier {
  private static String inventory =
      "minecraft:textures/gui/container/inventory.png";
  private static String absorption =
      "rewind:textures/gui/container/absorption.png";

  @Override
  public InputStream modifyResource(ResourceLocation location,
                                    InputStream inputStream)
      throws IOException {
    BufferedImage inventoryImage = ImageIO.read(inputStream);
    BufferedImage absorptionImage = ImageIO.read(ReWind.getInputStream(
        "assets/rewind/textures/gui/container/absorption.png"));
    BufferedImage merged =
        TextureEditor.mergeImage(inventoryImage, absorptionImage, 36, 234);
    return ResourcePackUtil.toPNGByteArrayInputStream(merged);
  }

  @Override
  public boolean resourceExists(ResourceLocation location) {
    return location.toString().equals(inventory);
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
    return "TraditionalAbsorptionResourcePack";
  }
}
