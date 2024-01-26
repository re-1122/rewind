package com.koyomiji.rewind;

import net.minecraft.block.material.MapColor;
import net.minecraft.item.EnumDyeColor;

import java.util.HashMap;
import java.util.Map;

public class DyeUtil {
  private static final Map<EnumDyeColor, MapColor> DYE_TO_MAP_COLOR =
          new HashMap<>();

  static {
    DYE_TO_MAP_COLOR.put(EnumDyeColor.WHITE, MapColor.SNOW);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.ORANGE, MapColor.ADOBE);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.MAGENTA, MapColor.MAGENTA);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.LIGHT_BLUE, MapColor.LIGHT_BLUE);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.YELLOW, MapColor.YELLOW);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.LIME, MapColor.LIME);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.PINK, MapColor.PINK);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.GRAY, MapColor.GRAY);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.SILVER, MapColor.SILVER);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.CYAN, MapColor.CYAN);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.PURPLE, MapColor.PURPLE);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.BLUE, MapColor.BLUE);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.BROWN, MapColor.BROWN);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.GREEN, MapColor.GREEN);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.RED, MapColor.RED);
    DYE_TO_MAP_COLOR.put(EnumDyeColor.BLACK, MapColor.BLACK);
  }

  public static MapColor getMapColor(EnumDyeColor dye) {
    return DYE_TO_MAP_COLOR.get(dye);
  }
}
