package com.koyomiji.rewind.proxy;

import com.koyomiji.refound.ReFoundCreativeTabs;
import com.koyomiji.refound.config.ReFoundConfig;
import com.koyomiji.rewind.ReWind;
import com.koyomiji.rewind.SoundRegistry;
import com.koyomiji.rewind.SoundTypes;
import com.koyomiji.rewind.config.ReWindConfig;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ReWind.MODID)
public class CommonProxy {

  public void preInit(FMLPreInitializationEvent event) {}

  public void init(FMLInitializationEvent event) {}

  public void postInit(FMLPostInitializationEvent event) {}

  private static SoundEvent createSoundEvent(String path) {
    ResourceLocation location = new ResourceLocation(ReWind.MODID, path);
    return new SoundEvent(location).setRegistryName(location);
  }

  @SubscribeEvent
  public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
    event.getRegistry().registerAll(
        SoundRegistry.BLOCK_CHEST_OPEN = createSoundEvent("block.chest.open"),
        SoundRegistry.BLOCK_CHEST_CLOSE = createSoundEvent("block.chest.close"),
        SoundRegistry.BLOCK_DOOR_OPEN = createSoundEvent("block.door.open"),
        SoundRegistry.BLOCK_DOOR_CLOSE = createSoundEvent("block.door.close"),
        SoundRegistry.BLOCK_TRAPDOOR_OPEN =
            createSoundEvent("block.trapdoor.open"),
        SoundRegistry.BLOCK_TRAPDOOR_CLOSE =
            createSoundEvent("block.trapdoor.close"),
        SoundRegistry.BLOCK_FENCE_GATE_OPEN =
            createSoundEvent("block.fence_gate.open"),
        SoundRegistry.BLOCK_FENCE_GATE_CLOSE =
            createSoundEvent("block.fence_gate.close"),
        SoundRegistry.BLOCK_GLASS_PLACE =
            createSoundEvent("block.glass.place"));
  }
}
