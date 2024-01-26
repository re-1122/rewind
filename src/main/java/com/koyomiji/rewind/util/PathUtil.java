package com.koyomiji.rewind.util;

import org.apache.commons.io.FilenameUtils;

public class PathUtil {
  public static String concatUnix(String path1, String path2) {
    return FilenameUtils.separatorsToUnix(FilenameUtils.concat(path1, path2));
  }
}
