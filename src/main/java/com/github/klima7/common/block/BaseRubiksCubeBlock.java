package com.github.klima7.common.block;

import com.github.klima7.client.KeyInit;
import com.github.klima7.common.entity.BaseRubiksCubeBlockEntity;
import com.github.klima7.core.init.PacketHandler;
import com.github.klima7.core.init.SoundRegistry;
import com.github.klima7.core.network.ServerboundUpdateRubiksCubePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class BaseRubiksCubeBlock extends Block implements EntityBlock {

    public static final SoundType SOUND = new SoundType(1.0F, 1.0F,
            SoundRegistry.HIT.get(),            // break
            SoundType.WOOD.getStepSound(),      // step
            SoundRegistry.PLACE.get(),          // place
            SoundRegistry.HIT.get(),            // hit
            SoundRegistry.HIT.get()             // fall
    );

    public static final Properties PROPERTIES = Properties
            .of(Material.CLAY)
            .color(MaterialColor.COLOR_BLACK)
            .strength(0.5f)
            .noOcclusion()
            .sound(SOUND);

    public BaseRubiksCubeBlock() {
        super(PROPERTIES);
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null :(_level, _pos, _state, blockEntity) ->
                ((BaseRubiksCubeBlockEntity) blockEntity).serverTick();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        Direction direction = hitResult.getDirection();
        boolean isReversed = KeyInit.REVERSE.isDown();
        boolean isRotation = KeyInit.ROTATE.isDown();
        UUID playerUUID = player.getUUID();

        PacketHandler.CHANNEL.sendToServer(
                new ServerboundUpdateRubiksCubePacket(pos, playerUUID, direction, isReversed, isRotation, true)
        );

        return InteractionResult.SUCCESS;
    }

}