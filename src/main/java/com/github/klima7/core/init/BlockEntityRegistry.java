package com.github.klima7.core.init;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.entity.CubeStandBlockEntity;
import com.github.klima7.common.entity.StandardRubiksCubeBlockEntity;
import com.github.klima7.common.entity.StickerlessRubiksCubeBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RubiksCubeMod.MODID);

    public static final RegistryObject<BlockEntityType<StandardRubiksCubeBlockEntity>> STANDARD_RUBIKS_CUBE = BLOCK_ENTITIES
            .register("standard_rubiks_cube_block_entity", () -> BlockEntityType.Builder
                    .of(StandardRubiksCubeBlockEntity::new, BlockRegistry.STANDARD_RUBIKS_CUBE.get()).build(null));

    public static final RegistryObject<BlockEntityType<StickerlessRubiksCubeBlockEntity>> STICKERLESS_RUBIKS_CUBE = BLOCK_ENTITIES
            .register("stickerless_rubiks_cube_block_entity", () -> BlockEntityType.Builder
                    .of(StickerlessRubiksCubeBlockEntity::new, BlockRegistry.STICKERLESS_RUBIKS_CUBE.get()).build(null));

    public static final RegistryObject<BlockEntityType<CubeStandBlockEntity>> CUBE_STAND = BLOCK_ENTITIES
            .register("cube_stand_block_entity", () -> BlockEntityType.Builder
                    .of(CubeStandBlockEntity::new, BlockRegistry.CUBE_STAND.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
