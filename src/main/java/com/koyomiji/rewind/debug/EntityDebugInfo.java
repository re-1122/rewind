package com.koyomiji.rewind.debug;

public class EntityDebugInfo {
  int entitiesRendered;
  int entitiesTotal;
  int entitiesHidden;

  public EntityDebugInfo(int entitiesRendered, int entitiesTotal,
                         int entitiesHidden) {
    this.entitiesRendered = entitiesRendered;
    this.entitiesTotal = entitiesTotal;
    this.entitiesHidden = entitiesHidden;
  }
}
