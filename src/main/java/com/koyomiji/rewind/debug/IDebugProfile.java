package com.koyomiji.rewind.debug;

import java.util.List;

public interface IDebugProfile {
  List<String> getLeftPrimary();

  List<String> getLeftSecondary();

  List<String> getRight();
}
