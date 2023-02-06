package com.github.klima7.domain.operation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockedCheckStrategy {

    public boolean isBlocked(Operation operation, BlockPos position, Level level) {
        long blockingCount = operation
                .getRequiredFreeDirections()
                .stream()
                .map(position::relative)
                .map(level::getBlockState)
                .filter(this::isBlockBlocking)
                .count();

        return blockingCount > 0;
    }

    private boolean isBlockBlocking(BlockState blockState) {
        return blockState.getMaterial().blocksMotion();
    }

}
