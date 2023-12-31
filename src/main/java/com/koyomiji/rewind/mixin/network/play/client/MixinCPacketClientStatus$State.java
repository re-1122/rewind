package com.koyomiji.rewind.mixin.network.play.client;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.network.play.client.CPacketClientStatus;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

// https://github.com/SpongePowered/Mixin/issues/387#issuecomment-888408556

@Mixin(CPacketClientStatus.State.class)
public class MixinCPacketClientStatus$State {
  @Shadow @Final @Mutable private static CPacketClientStatus.State[] $VALUES;
  private static CPacketClientStatus.State OPEN_INVENTORY_ACHIEVEMENT =
      add("OPEN_INVENTORY_ACHIEVEMENT");

  @Invoker("<init>")
  public static CPacketClientStatus.State invokeInit(String internalName,
                                                     int internalId) {
    throw new AssertionError();
  }

  @Unique
  private static CPacketClientStatus.State add(String internalName) {
    ArrayList<CPacketClientStatus.State> values =
        new ArrayList<CPacketClientStatus.State>(Arrays.asList($VALUES));
    CPacketClientStatus.State state =
        invokeInit(internalName, values.get(values.size() - 1).ordinal() + 1);
    values.add(state);
    $VALUES = values.toArray(new CPacketClientStatus.State[0]);
    return state;
  }
}
