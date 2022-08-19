package com.github.klima7.registry;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.entities.RubiksCubeTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileRegistry {

    private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RubiksCubeMod.MODID);

    public static final RegistryObject<BlockEntityType<RubiksCubeTileEntity>> RUBIKS_CUBE = TILES
            .register("rubiks_cube_tile", () -> BlockEntityType.Builder
                    .of(RubiksCubeTileEntity::new, BlockRegistry.RUBIKS_CUBE.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILES.register(eventBus);
    }

}
