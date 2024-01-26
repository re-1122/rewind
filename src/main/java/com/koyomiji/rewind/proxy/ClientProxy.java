package com.koyomiji.rewind.proxy;

import com.koyomiji.refound.interfaces.IDefaultResourcePacksAccessor;
import com.koyomiji.rewind.ReWind;
import com.koyomiji.rewind.config.ReWindConfig;
import com.koyomiji.rewind.resources.NoBlockRotateResourcePack;
import com.koyomiji.rewind.resources.TraditionalAbsorptionResourcePack;
import com.koyomiji.rewind.resources.TraditionalColorResourcePack;
import com.koyomiji.rewind.resources.TraditionalGrassResourcePack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = ReWind.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy {
  @Override
  public void preInit(FMLPreInitializationEvent event) {}

  @Override
  public void init(FMLInitializationEvent event) {
    boolean needReload = false;
    IDefaultResourcePacksAccessor accessor =
        ((IDefaultResourcePacksAccessor)Minecraft.getMinecraft());

    if (ReWindConfig.noBlockTextureRotation) {
      accessor.getDefaultResourcePacks().add(new NoBlockRotateResourcePack());
      needReload = true;
    }

    if (ReWindConfig.traditionalGrassTexture) {
      accessor.getDefaultResourcePacks().add(
          new TraditionalGrassResourcePack());
      needReload = true;
    }

    if (ReWindConfig.traditionalAbsorptionIcon) {
      accessor.getDefaultResourcePacks().add(
          new TraditionalAbsorptionResourcePack());
      needReload = true;
    }

    if (ReWindConfig.looksAndFeels.traditionalColors) {
      accessor.getDefaultResourcePacks().add(new TraditionalColorResourcePack());
      needReload = true;
    }

    if (needReload) {
      FMLClientHandler.instance().refreshResources();
    }
  }

  private static void registerItem(Item item, int meta, String name) {
    ModelLoader.setCustomModelResourceLocation(
        item, meta, new ModelResourceLocation(name, "inventory"));
  }

  @SubscribeEvent
  public static void registerModels(ModelRegistryEvent event) {
    if (ReWindConfig.traditionalBedIcon) {
      registerItem(Items.BED, EnumDyeColor.BLACK.getMetadata(),
                   "rewind:black_bed");
      registerItem(Items.BED, EnumDyeColor.RED.getMetadata(), "rewind:red_bed");
      registerItem(Items.BED, EnumDyeColor.GREEN.getMetadata(),
                   "rewind:green_bed");
      registerItem(Items.BED, EnumDyeColor.BROWN.getMetadata(),
                   "rewind:brown_bed");
      registerItem(Items.BED, EnumDyeColor.BLUE.getMetadata(),
                   "rewind:blue_bed");
      registerItem(Items.BED, EnumDyeColor.PURPLE.getMetadata(),
                   "rewind:purple_bed");
      registerItem(Items.BED, EnumDyeColor.CYAN.getMetadata(),
                   "rewind:cyan_bed");
      registerItem(Items.BED, EnumDyeColor.SILVER.getMetadata(),
                   "rewind:silver_bed");
      registerItem(Items.BED, EnumDyeColor.GRAY.getMetadata(),
                   "rewind:gray_bed");
      registerItem(Items.BED, EnumDyeColor.PINK.getMetadata(),
                   "rewind:pink_bed");
      registerItem(Items.BED, EnumDyeColor.LIME.getMetadata(),
                   "rewind:lime_bed");
      registerItem(Items.BED, EnumDyeColor.YELLOW.getMetadata(),
                   "rewind:yellow_bed");
      registerItem(Items.BED, EnumDyeColor.LIGHT_BLUE.getMetadata(),
                   "rewind:light_blue_bed");
      registerItem(Items.BED, EnumDyeColor.MAGENTA.getMetadata(),
                   "rewind:magenta_bed");
      registerItem(Items.BED, EnumDyeColor.ORANGE.getMetadata(),
                   "rewind:orange_bed");
      registerItem(Items.BED, EnumDyeColor.WHITE.getMetadata(),
                   "rewind:white_bed");
    }
  }
}
