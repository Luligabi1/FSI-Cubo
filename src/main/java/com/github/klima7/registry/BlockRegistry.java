package com.github.klima7.registry;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.blocks.RubiksCubeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RubiksCubeMod.MODID);

    public static final RegistryObject<RubiksCubeBlock> RUBIKS_CUBE = BLOCKS.register("rubiks_cube", RubiksCubeBlock::new);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
