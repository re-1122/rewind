package com.koyomiji.rewind;

import com.koyomiji.refound.RecipeUnregisterer;
import com.koyomiji.refound.setup.SetupQueue;
import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.proxy.CommonProxy;
import com.koyomiji.rewind.setup.Setup;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarFile;
import net.minecraft.command.server.CommandAchievement;
import net.minecraft.init.Biomes;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ReWind.MODID, dependencies = "required-after:refound@[0.2.0,)")
public class ReWind {
  public static final String MODID = "rewind";
  @Mod.Instance public static ReWind instance;
  @SidedProxy(clientSide = "com.koyomiji.rewind.proxy.ClientProxy",
              serverSide = "com.koyomiji.rewind.proxy.ServerProxy")
  public static CommonProxy proxy;
  public static Logger logger;
  public static File modFile;
  public static boolean isDeobfuscatedEnvironment;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) throws IOException {
    logger = event.getModLog();

    if (ReWindConfig.disableShieldRecipe) {
      RecipeUnregisterer.unregisterRecipe(
          new ResourceLocation("minecraft:shield"));
      RecipeUnregisterer.unregisterAdvancement(
          new ResourceLocation("minecraft:recipes/combat/shield"));
    }

    modFile = event.getSourceFile();
    isDeobfuscatedEnvironment =
        (boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
    proxy.preInit(event);
    SetupQueue.addSetupProcess(new Setup());
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);

    if (ReWindConfig.noTaigaVillage) {
      BiomeManager.removeVillageBiome(Biomes.TAIGA);
    }
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }

  @Mod.EventHandler
  public void serverStarting(FMLServerStartingEvent event) {
    event.registerServerCommand(new CommandAchievement());
  }

  public static InputStream getInputStream(String pathStr) {
    if (isDeobfuscatedEnvironment) {
      Path path = modFile.toPath().resolve(pathStr);
      try {
        return Files.newInputStream(path);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      try {
        JarFile modFileJar = new JarFile(modFile);
        return modFileJar.getInputStream(modFileJar.getJarEntry(pathStr));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
