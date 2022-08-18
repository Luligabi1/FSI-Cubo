package com.github.klima7;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RubiksCubeMod.MODID)
public class RubiksCubeMod
{
    public static final String MODID = "rubiks_cube";


    public RubiksCubeMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
