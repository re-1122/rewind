package com.koyomiji.rewind.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;

public interface ISidedShootable {
  void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn,
             float pitchOffset, float velocity, float inaccuracy,
             EnumHandSide hand);
}