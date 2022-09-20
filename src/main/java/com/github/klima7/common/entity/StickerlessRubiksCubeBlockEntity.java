package com.github.klima7.common.entity;

import com.github.klima7.core.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class StickerlessRubiksCubeBlockEntity extends BaseRubiksCubeBlockEntity {

    public static final String CONTROLLER_NAME = "stickerless_rubiks_cube_block_controller";

    public StickerlessRubiksCubeBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, BlockEntityRegistry.STICKERLESS_RUBIKS_CUBE.get(), CONTROLLER_NAME);
    }

}
