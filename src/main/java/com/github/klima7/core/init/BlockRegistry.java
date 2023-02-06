package com.github.klima7.core.init;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.block.CubeStandBlock;
import com.github.klima7.common.block.StandardRubiksCubeBlock;
import com.github.klima7.common.block.StickerlessRubiksCubeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RubiksCubeMod.MODID);

    public static final RegistryObject<StandardRubiksCubeBlock> STANDARD_RUBIKS_CUBE =
            BLOCKS.register("standard_rubiks_cube", StandardRubiksCubeBlock::new);

    public static final RegistryObject<StickerlessRubiksCubeBlock> STICKERLESS_RUBIKS_CUBE =
            BLOCKS.register("stickerless_rubiks_cube", StickerlessRubiksCubeBlock::new);

    public static final RegistryObject<CubeStandBlock> CUBE_STAND =
            BLOCKS.register("cube_stand", CubeStandBlock::new);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
