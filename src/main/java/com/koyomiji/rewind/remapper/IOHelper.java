package com.koyomiji.rewind.remapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class IOHelper {
  public static byte[] readAllBytes(InputStream is) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len = 0;
    while (true) {
      try {
        if ((len = is.read(buffer)) == -1)
          break;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      baos.write(buffer, 0, len);
    }
    return baos.toByteArray();
  }

  public static String readAllUTF8(InputStream is) {
    return new String(readAllBytes(is), StandardCharsets.UTF_8);
  }

  public static String readAllUTF8(Path path) {
    try {
      return readAllUTF8(path.toUri().toURL().openStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
