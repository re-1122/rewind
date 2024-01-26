package com.koyomiji.rewind.util;

import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;

public class HandUtil {
  public static EnumHandSide toHandSide(EnumHandSide primaryHand,
                                        EnumHand hand) {
    return hand == EnumHand.MAIN_HAND ? primaryHand : primaryHand.opposite();
  }
}
