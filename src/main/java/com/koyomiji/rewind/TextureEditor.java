package com.koyomiji.rewind;

import java.awt.image.BufferedImage;

public class TextureEditor {
  public static BufferedImage cropImage(BufferedImage image, int x, int y,
                                        int width, int height) {
    return image.getSubimage(x, y, width, height);
  }

  public static BufferedImage mergeImage(BufferedImage image1,
                                         BufferedImage image2, int x, int y) {
    BufferedImage image = new BufferedImage(
        image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_ARGB);
    image.getGraphics().drawImage(image1, 0, 0, null);
    image.getGraphics().drawImage(image2, x, y, null);
    return image;
  }

  public enum Orientation { HORIZONTAL, VERTICAL }

  /*
   * Flips an image horizontally or vertically.
   */
  public static BufferedImage flipImage(BufferedImage image, int x, int y,
                                        int width, int height,
                                        Orientation orientation) {
    BufferedImage flipped = new BufferedImage(
        image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    flipped.getGraphics().drawImage(image, 0, 0, null);

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int color = image.getRGB(x + i, y + j);

        if (orientation == Orientation.HORIZONTAL) {
          flipped.setRGB(x + width - i - 1, y + j, color);
        } else {
          flipped.setRGB(x + i, y + height - j - 1, color);
        }
      }
    }

    return flipped;
  }
}
