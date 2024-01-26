package com.koyomiji.rewind.resources;

import com.google.common.collect.ImmutableSet;
import com.koyomiji.rewind.ReWind;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

import com.koyomiji.rewind.util.PathHelper;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class TraditionalColorResourcePack implements IResourcePack {
  private static final Map<String, String> map = new HashMap<>();

  static {
    map.put("minecraft:textures/entity/bed/black.png", "rewind:textures/entity/bed/black_old.png");
    map.put("minecraft:textures/entity/bed/blue.png", "rewind:textures/entity/bed/blue_old.png");
    map.put("minecraft:textures/entity/bed/brown.png", "rewind:textures/entity/bed/brown_old.png");
    map.put("minecraft:textures/entity/bed/cyan.png", "rewind:textures/entity/bed/cyan_old.png");
    map.put("minecraft:textures/entity/bed/gray.png", "rewind:textures/entity/bed/gray_old.png");
    map.put("minecraft:textures/entity/bed/green.png", "rewind:textures/entity/bed/green_old.png");
    map.put("minecraft:textures/entity/bed/light_blue.png", "rewind:textures/entity/bed/light_blue_old.png");
    map.put("minecraft:textures/entity/bed/lime.png", "rewind:textures/entity/bed/lime_old.png");
    map.put("minecraft:textures/entity/bed/magenta.png", "rewind:textures/entity/bed/magenta_old.png");
    map.put("minecraft:textures/entity/bed/orange.png", "rewind:textures/entity/bed/orange_old.png");
    map.put("minecraft:textures/entity/bed/pink.png", "rewind:textures/entity/bed/pink_old.png");
    map.put("minecraft:textures/entity/bed/purple.png", "rewind:textures/entity/bed/purple_old.png");
    map.put("minecraft:textures/entity/bed/red.png", "rewind:textures/entity/bed/red_old.png");
    map.put("minecraft:textures/entity/bed/silver.png", "rewind:textures/entity/bed/silver_old.png");
    map.put("minecraft:textures/entity/bed/white.png", "rewind:textures/entity/bed/white_old.png");
    map.put("minecraft:textures/entity/bed/yellow.png", "rewind:textures/entity/bed/yellow_old.png");
    map.put("minecraft:textures/entity/bed/black_old.png", "rewind:textures/entity/bed/black.png");
    map.put("minecraft:textures/entity/shulker/shulker_black.png", "rewind:textures/entity/shulker/shulker_black_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_blue.png", "rewind:textures/entity/shulker/shulker_blue_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_brown.png", "rewind:textures/entity/shulker/shulker_brown_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_cyan.png", "rewind:textures/entity/shulker/shulker_cyan_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_gray.png", "rewind:textures/entity/shulker/shulker_gray_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_green.png", "rewind:textures/entity/shulker/shulker_green_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_light_blue.png", "rewind:textures/entity/shulker/shulker_light_blue_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_lime.png", "rewind:textures/entity/shulker/shulker_lime_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_magenta.png", "rewind:textures/entity/shulker/shulker_magenta_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_orange.png", "rewind:textures/entity/shulker/shulker_orange_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_pink.png", "rewind:textures/entity/shulker/shulker_pink_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_red.png", "rewind:textures/entity/shulker/shulker_red_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_silver.png", "rewind:textures/entity/shulker/shulker_silver_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_white.png", "rewind:textures/entity/shulker/shulker_white_old.png");
    map.put("minecraft:textures/entity/shulker/shulker_yellow.png", "rewind:textures/entity/shulker/shulker_yellow_old.png");
    map.put("minecraft:textures/blocks/shulker_top_black.png", "rewind:textures/blocks/shulker_top_black_old.png");
    map.put("minecraft:textures/blocks/shulker_top_blue.png", "rewind:textures/blocks/shulker_top_blue_old.png");
    map.put("minecraft:textures/blocks/shulker_top_brown.png", "rewind:textures/blocks/shulker_top_brown_old.png");
    map.put("minecraft:textures/blocks/shulker_top_cyan.png", "rewind:textures/blocks/shulker_top_cyan_old.png");
    map.put("minecraft:textures/blocks/shulker_top_gray.png", "rewind:textures/blocks/shulker_top_gray_old.png");
    map.put("minecraft:textures/blocks/shulker_top_green.png", "rewind:textures/blocks/shulker_top_green_old.png");
    map.put("minecraft:textures/blocks/shulker_top_light_blue.png", "rewind:textures/blocks/shulker_top_light_blue_old.png");
    map.put("minecraft:textures/blocks/shulker_top_lime.png", "rewind:textures/blocks/shulker_top_lime_old.png");
    map.put("minecraft:textures/blocks/shulker_top_magenta.png", "rewind:textures/blocks/shulker_top_magenta_old.png");
    map.put("minecraft:textures/blocks/shulker_top_orange.png", "rewind:textures/blocks/shulker_top_orange_old.png");
    map.put("minecraft:textures/blocks/shulker_top_pink.png", "rewind:textures/blocks/shulker_top_pink_old.png");
    map.put("minecraft:textures/blocks/shulker_top_red.png", "rewind:textures/blocks/shulker_top_red_old.png");
    map.put("minecraft:textures/blocks/shulker_top_silver.png", "rewind:textures/blocks/shulker_top_silver_old.png");
    map.put("minecraft:textures/blocks/shulker_top_white.png", "rewind:textures/blocks/shulker_top_white_old.png");
    map.put("minecraft:textures/blocks/shulker_top_yellow.png", "rewind:textures/blocks/shulker_top_yellow_old.png");
    map.put("minecraft:textures/blocks/wool_colored_black.png", "rewind:textures/blocks/wool_colored_black_old.png");
    map.put("minecraft:textures/blocks/wool_colored_blue.png", "rewind:textures/blocks/wool_colored_blue_old.png");
    map.put("minecraft:textures/blocks/wool_colored_brown.png", "rewind:textures/blocks/wool_colored_brown_old.png");
    map.put("minecraft:textures/blocks/wool_colored_cyan.png", "rewind:textures/blocks/wool_colored_cyan_old.png");
    map.put("minecraft:textures/blocks/wool_colored_gray.png", "rewind:textures/blocks/wool_colored_gray_old.png");
    map.put("minecraft:textures/blocks/wool_colored_green.png", "rewind:textures/blocks/wool_colored_green_old.png");
    map.put("minecraft:textures/blocks/wool_colored_light_blue.png", "rewind:textures/blocks/wool_colored_light_blue_old.png");
    map.put("minecraft:textures/blocks/wool_colored_lime.png", "rewind:textures/blocks/wool_colored_lime_old.png");
    map.put("minecraft:textures/blocks/wool_colored_magenta.png", "rewind:textures/blocks/wool_colored_magenta_old.png");
    map.put("minecraft:textures/blocks/wool_colored_orange.png", "rewind:textures/blocks/wool_colored_orange_old.png");
    map.put("minecraft:textures/blocks/wool_colored_pink.png", "rewind:textures/blocks/wool_colored_pink_old.png");
    map.put("minecraft:textures/blocks/wool_colored_purple.png", "rewind:textures/blocks/wool_colored_purple_old.png");
    map.put("minecraft:textures/blocks/wool_colored_red.png", "rewind:textures/blocks/wool_colored_red_old.png");
    map.put("minecraft:textures/blocks/wool_colored_silver.png", "rewind:textures/blocks/wool_colored_silver_old.png");
    map.put("minecraft:textures/blocks/wool_colored_white.png", "rewind:textures/blocks/wool_colored_white_old.png");
    map.put("minecraft:textures/blocks/wool_colored_yellow.png", "rewind:textures/blocks/wool_colored_yellow_old.png");
    map.put("rewind:models/item/black_bed.json", "rewind:models/item/black_bed_old.json");
    map.put("rewind:models/item/blue_bed.json", "rewind:models/item/blue_bed_old.json");
    map.put("rewind:models/item/brown_bed.json", "rewind:models/item/brown_bed_old.json");
    map.put("rewind:models/item/cyan_bed.json", "rewind:models/item/cyan_bed_old.json");
    map.put("rewind:models/item/gray_bed.json", "rewind:models/item/gray_bed_old.json");
    map.put("rewind:models/item/green_bed.json", "rewind:models/item/green_bed_old.json");
    map.put("rewind:models/item/light_blue_bed.json", "rewind:models/item/light_blue_bed_old.json");
    map.put("rewind:models/item/lime_bed.json", "rewind:models/item/lime_bed_old.json");
    map.put("rewind:models/item/magenta_bed.json", "rewind:models/item/magenta_bed_old.json");
    map.put("rewind:models/item/orange_bed.json", "rewind:models/item/orange_bed_old.json");
    map.put("rewind:models/item/pink_bed.json", "rewind:models/item/pink_bed_old.json");
    map.put("rewind:models/item/purple_bed.json", "rewind:models/item/purple_bed_old.json");
    map.put("rewind:models/item/red_bed.json", "rewind:models/item/red_bed_old.json");
    map.put("rewind:models/item/silver_bed.json", "rewind:models/item/silver_bed_old.json");
    map.put("rewind:models/item/white_bed.json", "rewind:models/item/white_bed_old.json");
    map.put("rewind:models/item/yellow_bed.json", "rewind:models/item/yellow_bed_old.json");
  }

  @Override
  public InputStream getInputStream(ResourceLocation location) throws IOException {
    ResourceLocation mapped = new ResourceLocation(map.get(location.toString()));
    return ReWind.getInputStream(
            PathHelper.concatUnix("assets/rewind/", mapped.getPath()));
  }

  @Override
  public boolean resourceExists(ResourceLocation location) {
    ReWind.logger.info(location);
    return map.containsKey(location.toString());
  }

  @Override
  public Set<String> getResourceDomains() {
    return ImmutableSet.of("minecraft", "rewind");
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
    return "TraditionalColorResourcePack";
  }
}
