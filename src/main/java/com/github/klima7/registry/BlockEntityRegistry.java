package com.github.klima7.registry;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.entities.RubiksCubeBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RubiksCubeMod.MODID);

    public static final RegistryObject<BlockEntityType<RubiksCubeBlockEntity>> RUBIKS_CUBE = BLOCK_ENTITIES
            .register("rubiks_cube_block_entity", () -> BlockEntityType.Builder
                    .of(RubiksCubeBlockEntity::new, BlockRegistry.RUBIKS_CUBE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
