package com.github.klima7.common.block;

import com.github.klima7.common.entity.RubiksCubeBlockEntity;
import com.github.klima7.core.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RubiksCubeBlock extends Block implements EntityBlock {

    public static final Properties PROPERTIES = BlockBehaviour.Properties
            .of(Material.WOOD)
            .strength(8)
            .explosionResistance(30f)
            .noOcclusion();

    public RubiksCubeBlock() {
        super(PROPERTIES);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return BlockEntityRegistry.RUBIKS_CUBE.get().create(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @javax.annotation.Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return level.isClientSide() ? null :(_level, _pos, _state, blockEntity) ->
                ((RubiksCubeBlockEntity) blockEntity).tick();
    }
}
