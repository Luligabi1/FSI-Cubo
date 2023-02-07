package com.github.klima7.common.block;

import com.github.klima7.common.entity.CubeStandBlockEntity;
import com.github.klima7.common.item.BaseRubiksCubeItem;
import com.github.klima7.core.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class CubeStandBlock extends Block implements EntityBlock {

    public static final Properties PROPERTIES = BlockBehaviour.Properties.of(Material.DECORATION)
            .sound(SoundType.GLASS)
            .instabreak()
            .noOcclusion();

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 0, 5, 11, 1, 11),
            Block.box(6, 1, 6, 10, 2, 10),
            Block.box(7, 2, 7, 9, 3, 9),
            Block.box(5.6, 3, 5.6, 10.4, 7.8, 10.4)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

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

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos pos1, Block block, BlockPos pos2, boolean param) {
        if (!blockState.canSurvive(level, pos1)) {
            BlockEntity blockentity = blockState.hasBlockEntity() ? level.getBlockEntity(pos1) : null;
            dropResources(blockState, level, pos1, blockentity);
            level.removeBlock(pos1, false);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState block, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {

        if (!level.isClientSide && level.getBlockEntity(pos) instanceof final CubeStandBlockEntity entity) {
            if(entity.isEmpty()) {
                ItemStack item = player.getMainHandItem();
                if(item.getItem() instanceof BaseRubiksCubeItem) {
                    entity.placeCube(item);
                    return InteractionResult.SUCCESS;
                }
            } else {
                entity.takeCube(player);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null :(_level, _pos, _state, blockEntity) ->
                ((CubeStandBlockEntity) blockEntity).tick();
    }
}
