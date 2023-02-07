package com.github.klima7.common.block;

import com.github.klima7.core.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class CubeStandBlock extends Block implements EntityBlock {

    public static final Properties PROPERTIES = BlockBehaviour.Properties.of(Material.DECORATION)
            .sound(SoundType.GLASS)
            .instabreak()
            .noOcclusion();

    public CubeStandBlock() {
        super(PROPERTIES);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return BlockEntityRegistry.CUBE_STAND.get().create(blockPos, blockState);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
        return canSupportRigidBlock(levelReader, pos.below());
    }

    public void neighborChanged(BlockState blockState, Level level, BlockPos pos1, Block block, BlockPos pos2, boolean param) {
        if (!blockState.canSurvive(level, pos1)) {
            BlockEntity blockentity = blockState.hasBlockEntity() ? level.getBlockEntity(pos1) : null;
            dropResources(blockState, level, pos1, blockentity);
            level.removeBlock(pos1, false);
        }
    }

}
