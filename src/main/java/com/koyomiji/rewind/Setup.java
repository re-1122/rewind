package com.koyomiji.rewind;

import com.google.common.collect.Maps;
import com.koyomiji.refound.asset.AssetFetcher;
import com.koyomiji.refound.asset.FileInjector;
import com.koyomiji.rewind.remapper.DirectClassRemapper;
import com.koyomiji.rewind.remapper.FMLClassRemapper;
import com.koyomiji.rewind.remapper.IOHelper;
import com.koyomiji.rewind.remapper.Mapping;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class Setup {
  private static boolean initialRun = false;

  private static final String[] ACHIEVEMENT_CLASSES = {
      "net/minecraft/client/gui/achievement/GuiAchievement",
      "net/minecraft/client/gui/achievement/GuiAchievements",
      "net/minecraft/command/server/CommandAchievement",
      "net/minecraft/command/server/CommandAchievement$1",
      "net/minecraft/stats/Achievement",
      "net/minecraft/stats/AchievementList",
      "net/minecraft/util/JsonSerializableSet",
  };

  private static final String[] FILES = {
      "assets/minecraft/textures/gui/achievement/achievement_background.png",
  };

  private static final String LANG_EN_US_FILENAME =
      "assets/minecraft/lang/en_us.lang";

  private static String[] ACHIEVEMENT_LANG_KES = new String[] {
      "multiplayer.downloadingStats",
      "chat.type.achievement",
      "chat.type.achievement.taken",
      "demo.help.fullWrapped",
      "gui.achievements",
      "stats.tooltip.type.achievement",
      "achievement.get",
      "achievement.taken",
      "achievement.unknown",
      "achievement.requires",
      "achievement.openInventory",
      "achievement.openInventory.desc",
      "achievement.mineWood",
      "achievement.mineWood.desc",
      "achievement.buildWorkBench",
      "achievement.buildWorkBench.desc",
      "achievement.buildPickaxe",
      "achievement.buildPickaxe.desc",
      "achievement.buildFurnace",
      "achievement.buildFurnace.desc",
      "achievement.acquireIron",
      "achievement.acquireIron.desc",
      "achievement.buildHoe",
      "achievement.buildHoe.desc",
      "achievement.makeBread",
      "achievement.makeBread.desc",
      "achievement.bakeCake",
      "achievement.bakeCake.desc",
      "achievement.buildBetterPickaxe",
      "achievement.buildBetterPickaxe.desc",
      "achievement.overpowered",
      "achievement.overpowered.desc",
      "achievement.cookFish",
      "achievement.cookFish.desc",
      "achievement.onARail",
      "achievement.onARail.desc",
      "achievement.buildSword",
      "achievement.buildSword.desc",
      "achievement.killEnemy",
      "achievement.killEnemy.desc",
      "achievement.killCow",
      "achievement.killCow.desc",
      "achievement.breedCow",
      "achievement.breedCow.desc",
      "achievement.flyPig",
      "achievement.flyPig.desc",
      "achievement.snipeSkeleton",
      "achievement.snipeSkeleton.desc",
      "achievement.diamonds",
      "achievement.diamonds.desc",
      "achievement.diamondsToYou",
      "achievement.diamondsToYou.desc",
      "achievement.portal",
      "achievement.portal.desc",
      "achievement.ghast",
      "achievement.ghast.desc",
      "achievement.blazeRod",
      "achievement.blazeRod.desc",
      "achievement.potion",
      "achievement.potion.desc",
      "achievement.theEnd",
      "achievement.theEnd.desc",
      "achievement.theEnd2",
      "achievement.theEnd2.desc",
      "achievement.spawnWither",
      "achievement.spawnWither.desc",
      "achievement.killWither",
      "achievement.killWither.desc",
      "achievement.fullBeacon",
      "achievement.fullBeacon.desc",
      "achievement.exploreAllBiomes",
      "achievement.exploreAllBiomes.desc",
      "achievement.enchantments",
      "achievement.enchantments.desc",
      "achievement.overkill",
      "achievement.overkill.desc",
      "achievement.bookcase",
      "achievement.bookcase.desc",
      "commands.achievement.usage",
      "commands.achievement.unknownAchievement",
      "commands.achievement.alreadyHave",
      "commands.achievement.dontHave",
      "commands.achievement.give.success.all",
      "commands.achievement.give.success.one",
      "commands.achievement.take.success.all",
      "commands.achievement.take.success.one",
  };

  private static final Map<String, String> FILES_1_7_10 = Maps.newHashMap();

  static {
    FILES_1_7_10.put(
        "assets/minecraft/textures/blocks/double_plant_grass_bottom.png",
        "assets/rewind/textures/blocks/double_plant_grass_bottom.png");
    FILES_1_7_10.put(
        "assets/minecraft/textures/blocks/double_plant_grass_top.png",
        "assets/rewind/textures/blocks/double_plant_grass_top.png");
    FILES_1_7_10.put("assets/minecraft/textures/blocks/tallgrass.png",
                     "assets/rewind/textures/blocks/tallgrass.png");
  }

  private static final String CREDIT_FILENAME = "credits.txt";
  private static final String MAPPING_FILENAME = "mappings.json";

  private static Mapping getMapping() {
    return Mapping.fromJSON(
        IOHelper.readAllUTF8(ReWind.getInputStream(MAPPING_FILENAME)));
  }

  private static String classNameToPath(String className) {
    return className + ".class";
  }

  public static void setupIfNecessary() {
    FileInjector assetInjector = new FileInjector(ReWind.modFile);

    if (assetInjector.exists(CREDIT_FILENAME)) {
      return;
    }

    initialRun = true;
    CreditsGenerator cg = new CreditsGenerator();

    Mapping mapping = getMapping();
    AssetFetcher assetFetcher = new AssetFetcher();
    Path client1_11_2Path = assetFetcher.fetchAndGetPath(Assets.client1_11_2);

    try (JarFile client1_11_2 = new JarFile(client1_11_2Path.toFile())) {
      for (String className : ACHIEVEMENT_CLASSES) {
        String className1_11_2 = mapping.unmapClassName(className);
        JarEntry entry =
            client1_11_2.getJarEntry(classNameToPath(className1_11_2));
        InputStream is = client1_11_2.getInputStream(entry);
        byte[] bytes = IOHelper.readAllBytes(is);

        ClassWriter writer = new ClassWriter(0);
        FMLClassRemapper fcr = new FMLClassRemapper(writer);
        DirectClassRemapper dcr = new DirectClassRemapper(fcr, mapping);
        ClassReader reader = new ClassReader(bytes);
        reader.accept(dcr, 0);

        assetInjector.add(classNameToPath(className), writer.toByteArray());

        cg.add(Assets.client1_11_2, classNameToPath(className));
      }

      for (String file : FILES) {
        byte[] bytes = IOHelper.readAllBytes(
            client1_11_2.getInputStream(client1_11_2.getJarEntry(file)));
        assetInjector.add(file, bytes);
        cg.add(Assets.client1_11_2, file);
      }

      String langEnUs = IOHelper.readAllUTF8(client1_11_2.getInputStream(
          client1_11_2.getJarEntry(LANG_EN_US_FILENAME)));
      Map<String, String> enUs = RawLanguageMap.parse(langEnUs);
      assetInjector.add("assets/rewind/lang/en_us.lang",
                        RawLanguageMap.stringify(extractLang(enUs))
                            .getBytes(StandardCharsets.UTF_8));
      cg.add(Assets.client1_11_2, "assets/rewind/lang/en_us.lang");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Path client1_7_10Path = assetFetcher.fetchAndGetPath(Assets.client1_7_10);

    try (JarFile client1_7_10 = new JarFile(client1_7_10Path.toFile())) {
      for (Map.Entry<String, String> e : FILES_1_7_10.entrySet()) {
        byte[] bytes = IOHelper.readAllBytes(
            client1_7_10.getInputStream(client1_7_10.getJarEntry(e.getKey())));
        assetInjector.add(e.getValue(), bytes);
        cg.add(Assets.client1_7_10, e.getValue());
      }

      BufferedImage inventory =
          ImageIO.read(client1_7_10.getInputStream(client1_7_10.getEntry(
              "assets/minecraft/textures/gui/container/inventory.png")));
      BufferedImage absorption =
          TextureEditor.cropImage(inventory, 36, 234, 18, 18);
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageIO.write(absorption, "png", os);
      assetInjector.add("assets/rewind/textures/gui/container/absorption.png",
                        os.toByteArray());
      cg.add(Assets.client1_7_10,
             "assets/rewind/textures/gui/container/absorption.png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assetInjector.add(CREDIT_FILENAME,
                      cg.generate().getBytes(StandardCharsets.UTF_8));
    assetInjector.commit();

    JOptionPane.showMessageDialog(
        null, "ReWind has been set up successfully. Please restart the game.",
        "ReWind", JOptionPane.INFORMATION_MESSAGE);

    throw new IntentionalSetupException(
        "This is an intentional crash to force the game to restart. Please restart the game.");
  }

  private static Map<String, String> extractLang(Map<String, String> lang) {
    Map<String, String> extracted = new HashMap<>();

    for (String key : ACHIEVEMENT_LANG_KES) {
      extracted.put(key, lang.get(key));
    }

    return extracted;
  }
}
