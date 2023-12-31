package com.koyomiji.rewind.debug;

import java.util.List;
import net.minecraft.client.Minecraft;

public class DebugProfile1_12 extends DebugProfile1_8 {
  public DebugProfile1_12(Minecraft mc) { super(mc); }

  @Override
  protected String getDebugInfoEntitiesString(EntityDebugInfo info) {
    return "E: " + info.entitiesRendered + "/" + info.entitiesTotal +
        ", B: " + info.entitiesHidden;
  }

  @Override
  protected String getLocalDifficultyString(float additionalDifficulty,
                                            float clampedAdditionalDifficulty,
                                            long time) {
    return String.format("Local Difficulty: %.2f // %.2f (Day %d)",
                         additionalDifficulty, clampedAdditionalDifficulty,
                         time / 24000L);
  }

  public List<String> getLeftPrimary() {
    List<String> left = super.getLeftPrimary();
    left.add("");
    left.add("Debug: Pie [shift]: " +
             (mc.gameSettings.showDebugProfilerChart ? "visible" : "hidden") +
             " FPS [alt]: " +
             (mc.gameSettings.showLagometer ? "visible" : "hidden"));
    left.add("For help: press F3 + Q");
    return left;
  }
}
