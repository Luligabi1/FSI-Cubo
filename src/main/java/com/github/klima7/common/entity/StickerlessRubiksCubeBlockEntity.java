package com.github.klima7.common.entity;

import com.github.klima7.common.domain.operation.Operation;
import com.github.klima7.core.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class StickerlessRubiksCubeBlockEntity extends AbstractRubiksCubeBlockEntity {

    public static final String CONTROLLER_NAME = "stickerless_rubiks_cube_block_controller";

    public StickerlessRubiksCubeBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, BlockEntityRegistry.STICKERLESS_RUBIKS_CUBE.get(), CONTROLLER_NAME);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLong("startTime", startTime);
        if(operation != null)
            tag.put("operation", operation.save());
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.startTime = tag.getLong("startTime");
        this.operation = tag.contains("operation") ? Operation.load(tag.getCompound("operation")) : null;
    }

    @Override
    protected void applyOperation(Operation operation) {
    }

}
