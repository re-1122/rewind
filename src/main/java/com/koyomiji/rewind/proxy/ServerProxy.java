package com.koyomiji.rewind.proxy;

import com.koyomiji.rewind.ReWind;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = ReWind.MODID, value = Side.SERVER)
public class ServerProxy extends CommonProxy {}
