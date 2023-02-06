package com.github.klima7.common.entity;

import com.github.klima7.core.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CubeStandBlockEntity extends BlockEntity {

    public CubeStandBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CUBE_STAND.get(), pos, state);
    }

}
