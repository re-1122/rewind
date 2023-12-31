package com.koyomiji.rewind.remapper;

public class ObjectHelper {
  public static <T> T orDefault(T str, T def) {
    return str == null ? def : str;
  }
}
