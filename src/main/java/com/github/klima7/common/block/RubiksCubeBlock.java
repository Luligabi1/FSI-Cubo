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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RubiksCubeBlock extends Block implements EntityBlock {

    public static final Properties PROPERTIES = BlockBehaviour.Properties
            .of(Material.CLAY)
            .strength(0.5f)
            .noOcclusion();

    private Direction facing;   // Auxiliary variable to pass value between callback methods

    public RubiksCubeBlock() {
        super(PROPERTIES);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return BlockEntityRegistry.RUBIKS_CUBE.get().create(blockPos, blockState);
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

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity,
                            ItemStack itemStack) {
        RubiksCubeBlockEntity entity = (RubiksCubeBlockEntity) level.getBlockEntity(blockPos);
        entity.initializeFromItem(itemStack);
        if(this.facing != null) {
            entity.executeOperation(new InstantRotations(RotationsSet.createToDirection(this.facing)));
        }
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        dropResources(blockState, level, blockPos, blockEntity);
        super.playerWillDestroy(level, blockPos, blockState, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder lootContextBuilder) {
        RubiksCubeBlockEntity entity = (RubiksCubeBlockEntity) lootContextBuilder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        ItemStack item = entity.asItem();
        return List.of(item);
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
        boolean isReversed = KeyInit.REVERSE.isDown();
        boolean isRotation = KeyInit.ROTATE.isDown();

        PacketHandler.CHANNEL.sendToServer(
                new ServerboundUpdateRubiksCubePacket(pos, direction, isReversed, isRotation)
        );

        return InteractionResult.SUCCESS;
    }

}
