package com.github.klima7;

import com.github.klima7.core.init.BlockEntityRegistry;
import com.github.klima7.core.init.BlockRegistry;
import com.github.klima7.core.init.ItemRegistry;
import com.github.klima7.core.init.SoundRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RubiksCubeMod.MODID)
public class RubiksCubeMod {

    public static final String MODID = "rubiks_cube";

    public RubiksCubeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockRegistry.register(modEventBus);
        BlockEntityRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);
        SoundRegistry.register(modEventBus);
    }

}
