package com.koyomiji.rewind.setup;

import com.google.common.collect.Maps;
import com.koyomiji.refound.asset.AssetFetcher;
import com.koyomiji.refound.asset.AssetIdentifier;
import com.koyomiji.refound.asset.FileInjector;
import com.koyomiji.refound.setup.ISetupProcess;
import com.koyomiji.rewind.ReWind;
import com.koyomiji.rewind.TextureEditor;
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

public class Setup implements ISetupProcess {
  private static final String[] ACHIEVEMENT_CLASSES = {
          "net/minecraft/client/gui/achievement/GuiAchievement",
          "net/minecraft/client/gui/achievement/GuiAchievements",
          "net/minecraft/command/server/CommandAchievement",
          "net/minecraft/command/server/CommandAchievement$1",
          "net/minecraft/stats/Achievement",
          "net/minecraft/stats/AchievementList",
          "net/minecraft/util/JsonSerializableSet",
  };

  private static final Map<String, String> FILES_1_11_2 = Maps.newHashMap();

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

    FILES_1_11_2.put("assets/minecraft/textures/gui/achievement/achievement_background.png",
            "assets/minecraft/textures/gui/achievement/achievement_background.png");
    FILES_1_11_2.put("assets/minecraft/textures/items/bed.png",
            "assets/rewind/textures/items/bed_red_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_black.png",
            "assets/rewind/textures/blocks/wool_colored_black_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_blue.png",
            "assets/rewind/textures/blocks/wool_colored_blue_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_brown.png",
            "assets/rewind/textures/blocks/wool_colored_brown_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_cyan.png",
            "assets/rewind/textures/blocks/wool_colored_cyan_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_gray.png",
            "assets/rewind/textures/blocks/wool_colored_gray_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_green.png",
            "assets/rewind/textures/blocks/wool_colored_green_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_light_blue.png",
            "assets/rewind/textures/blocks/wool_colored_light_blue_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_lime.png",
            "assets/rewind/textures/blocks/wool_colored_lime_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_magenta.png",
            "assets/rewind/textures/blocks/wool_colored_magenta_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_orange.png",
            "assets/rewind/textures/blocks/wool_colored_orange_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_pink.png",
            "assets/rewind/textures/blocks/wool_colored_pink_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_purple.png",
            "assets/rewind/textures/blocks/wool_colored_purple_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_red.png",
            "assets/rewind/textures/blocks/wool_colored_red_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_silver.png",
            "assets/rewind/textures/blocks/wool_colored_silver_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_white.png",
            "assets/rewind/textures/blocks/wool_colored_white_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/wool_colored_yellow.png",
            "assets/rewind/textures/blocks/wool_colored_yellow_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_black.png",
            "assets/rewind/textures/blocks/shulker_top_black_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_blue.png",
            "assets/rewind/textures/blocks/shulker_top_blue_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_brown.png",
            "assets/rewind/textures/blocks/shulker_top_brown_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_cyan.png",
            "assets/rewind/textures/blocks/shulker_top_cyan_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_gray.png",
            "assets/rewind/textures/blocks/shulker_top_gray_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_green.png",
            "assets/rewind/textures/blocks/shulker_top_green_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_light_blue.png",
            "assets/rewind/textures/blocks/shulker_top_light_blue_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_lime.png",
            "assets/rewind/textures/blocks/shulker_top_lime_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_magenta.png",
            "assets/rewind/textures/blocks/shulker_top_magenta_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_orange.png",
            "assets/rewind/textures/blocks/shulker_top_orange_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_pink.png",
            "assets/rewind/textures/blocks/shulker_top_pink_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_red.png",
            "assets/rewind/textures/blocks/shulker_top_red_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_silver.png",
            "assets/rewind/textures/blocks/shulker_top_silver_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_white.png",
            "assets/rewind/textures/blocks/shulker_top_white_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/blocks/shulker_top_yellow.png",
            "assets/rewind/textures/blocks/shulker_top_yellow_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_black.png",
            "assets/rewind/textures/entity/shulker/shulker_black_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_blue.png",
            "assets/rewind/textures/entity/shulker/shulker_blue_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_brown.png",
            "assets/rewind/textures/entity/shulker/shulker_brown_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_cyan.png",
            "assets/rewind/textures/entity/shulker/shulker_cyan_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_gray.png",
            "assets/rewind/textures/entity/shulker/shulker_gray_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_green.png",
            "assets/rewind/textures/entity/shulker/shulker_green_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_light_blue.png",
            "assets/rewind/textures/entity/shulker/shulker_light_blue_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_lime.png",
            "assets/rewind/textures/entity/shulker/shulker_lime_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_magenta.png",
            "assets/rewind/textures/entity/shulker/shulker_magenta_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_orange.png",
            "assets/rewind/textures/entity/shulker/shulker_orange_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_pink.png",
            "assets/rewind/textures/entity/shulker/shulker_pink_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_red.png",
            "assets/rewind/textures/entity/shulker/shulker_red_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_silver.png",
            "assets/rewind/textures/entity/shulker/shulker_silver_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_white.png",
            "assets/rewind/textures/entity/shulker/shulker_white_old.png");
    FILES_1_11_2.put("assets/minecraft/textures/entity/shulker/shulker_yellow.png",
            "assets/rewind/textures/entity/shulker/shulker_yellow_old.png");
  }

  private static final String CREDITS_FILENAME = "credits.txt";
  private static final String MAPPING_FILENAME = "mappings.json";

  private static Mapping getMapping() {
    return Mapping.fromJSON(
            IOHelper.readAllUTF8(ReWind.getInputStream(MAPPING_FILENAME)));
  }

  private static String classNameToPath(String className) {
    return className + ".class";
  }

  private static Path fetchAndGetPath(AssetFetcher af, AssetIdentifier asset) {
    ReWind.logger.info("Fetching " + asset.url + "...");

    boolean didFetch = true;

    if (af.queryCache(asset) != null) {
      didFetch = false;
      ReWind.logger.info("Found " + asset.url + " in cache.");
    }

    Stopwatch sw = new Stopwatch();
    sw.start();
    Path result = af.fetchAndGetPath(asset);
    sw.stop();

    if (didFetch) {
      ReWind.logger.info("Fetched " + asset.url + " in " +
              sw.getElapsedInSeconds() + "s.");
    }

    return result;
  }

  private static Map<String, String> extractLang(Map<String, String> lang) {
    Map<String, String> extracted = new HashMap<>();

    for (String key : ACHIEVEMENT_LANG_KES) {
      extracted.put(key, lang.get(key));
    }

    return extracted;
  }

  @Override
  public String getModID() {
    return ReWind.MODID;
  }

  @Override
  public boolean needsSetup() {
    FileInjector assetInjector = new FileInjector(ReWind.modFile);
    return !assetInjector.exists(CREDITS_FILENAME);
  }

  @Override
  public boolean needsRestart() {
    return true;
  }

  @Override
  public void setup() {
    FileInjector assetInjector = new FileInjector(ReWind.modFile);

    ReWind.logger.info("Beginning setup...");
    Stopwatch swTotal = new Stopwatch();
    swTotal.start();
    Stopwatch sw = new Stopwatch();

    CreditsGenerator cg = new CreditsGenerator();

    Mapping mapping = getMapping();
    AssetFetcher assetFetcher = new AssetFetcher();
    Path client1_11_2Path = fetchAndGetPath(assetFetcher, Assets.client1_11_2);

    try (JarFile client1_11_2 = new JarFile(client1_11_2Path.toFile())) {
      for (String className : ACHIEVEMENT_CLASSES) {
        String className1_11_2 = mapping.unmapClassName(className);
        sw.start();

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

        sw.stop();
        ReWind.logger.info("Remapped " + className1_11_2 + " in " +
                sw.getElapsedInSeconds() + "s.");
      }

      for (Map.Entry<String, String> e : FILES_1_11_2.entrySet()) {
        sw.start();

        byte[] bytes = IOHelper.readAllBytes(
                client1_11_2.getInputStream(client1_11_2.getJarEntry(e.getKey())));
        assetInjector.add(e.getValue(), bytes);
        cg.add(Assets.client1_11_2, e.getValue());

        sw.stop();
        ReWind.logger.info("Extracted " + e.getValue() + " in " +
                sw.getElapsedInSeconds() + "s.");
      }

      sw.start();

      String langEnUs = IOHelper.readAllUTF8(client1_11_2.getInputStream(
              client1_11_2.getJarEntry(LANG_EN_US_FILENAME)));
      Map<String, String> enUs = RawLanguageMap.parse(langEnUs);
      assetInjector.add("assets/rewind/lang/en_us.lang",
              RawLanguageMap.stringify(extractLang(enUs))
                      .getBytes(StandardCharsets.UTF_8));
      cg.add(Assets.client1_11_2, "assets/rewind/lang/en_us.lang");

      sw.stop();
      ReWind.logger.info("Generated assets/rewind/lang/en_us.lang"
              + " in " + sw.getElapsedInSeconds() + "s.");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Path client1_7_10Path = fetchAndGetPath(assetFetcher, Assets.client1_7_10);

    try (JarFile client1_7_10 = new JarFile(client1_7_10Path.toFile())) {
      for (Map.Entry<String, String> e : FILES_1_7_10.entrySet()) {
        sw.start();

        byte[] bytes = IOHelper.readAllBytes(
                client1_7_10.getInputStream(client1_7_10.getJarEntry(e.getKey())));
        assetInjector.add(e.getValue(), bytes);
        cg.add(Assets.client1_7_10, e.getValue());

        sw.stop();
        ReWind.logger.info("Extracted " + e.getKey() + " in " +
                sw.getElapsedInSeconds() + "s.");
      }

      sw.start();

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

      sw.stop();
      ReWind.logger.info(
              "Generated assets/rewind/textures/gui/container/absorption.png in " +
                      sw.getElapsedInSeconds() + "s.");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assetInjector.add(CREDITS_FILENAME,
            cg.generate().getBytes(StandardCharsets.UTF_8));

    ReWind.logger.info("Injecting files...");
    sw.start();
    assetInjector.commit();
    sw.stop();
    ReWind.logger.info("Injected files in " + sw.getElapsedInSeconds() + "s.");

    swTotal.stop();
    ReWind.logger.info("Setup completed in " + swTotal.getElapsedInSeconds() +
            "s.");

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException |
             IllegalAccessException | UnsupportedLookAndFeelException e) {
      throw new RuntimeException(e);
    }

    JOptionPane.showMessageDialog(
            null, "ReWind has been set up successfully. Please restart the game.",
            "ReWind", JOptionPane.INFORMATION_MESSAGE);
  }
}
