package com.github.klima7.common.block;

import com.github.klima7.client.KeyInit;
import com.github.klima7.common.domain.operation.rotation.InstantRotations;
import com.github.klima7.common.domain.operation.rotation.RotationsSet;
import com.github.klima7.common.entity.RubiksCubeBlockEntity;
import com.github.klima7.core.init.BlockEntityRegistry;
import com.github.klima7.core.init.PacketHandler;
import com.github.klima7.core.network.ServerboundUpdateRubiksCubePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RubiksCubeBlock extends Block implements EntityBlock {

    public static final Properties PROPERTIES = BlockBehaviour.Properties
            .of(Material.WOOD)
            .strength(8)
            .explosionResistance(30f)
            .noOcclusion();

    private Direction facing;

    public RubiksCubeBlock() {
        super(PROPERTIES);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        RubiksCubeBlockEntity entity = BlockEntityRegistry.RUBIKS_CUBE.get().create(blockPos, blockState);
        if(this.facing != null) {
            entity.executeOperation(new InstantRotations(RotationsSet.createToDirection(this.facing)));
        }
        return entity;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        this.facing = context.getHorizontalDirection().getOpposite();
        return super.getStateForPlacement(context);
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null :(_level, _pos, _state, blockEntity) ->
                ((RubiksCubeBlockEntity) blockEntity).serverTick();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        Direction direction = hitResult.getDirection();
        boolean reverse = KeyInit.REVERSE.isDown();
        boolean rotate = KeyInit.ROTATE.isDown();

        System.out.println("Moving face " + direction + " reverse " + reverse);

        PacketHandler.CHANNEL.sendToServer(
                new ServerboundUpdateRubiksCubePacket(pos, direction, reverse, rotate)
        );

        return InteractionResult.SUCCESS;
    }

}
