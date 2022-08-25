package com.github.klima7.common.entity;

import com.github.klima7.core.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RubiksCubeBlockEntity extends BlockEntity implements IAnimatable {

    private static final String CONTROLLER_NAME = "rubiks_cube_block_controller";
    private static final int MOVE_DURATION = 20;

    private final AnimationFactory factory = new AnimationFactory(this);

    private boolean isMoving = false;
    private long startTime;

    public RubiksCubeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RUBIKS_CUBE.get(), pos, state);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, CONTROLLER_NAME, 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("isMoving", isMoving);
        tag.putLong("startTime", startTime);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.isMoving = tag.getBoolean("isMoving");
        this.startTime = tag.getLong("startTime");
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            load(tag);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
        CompoundTag tag = packet.getTag();
        if(tag != null) {
            load(tag);
        }
    }

    public void serverTick() {
        long currentTime = level.getGameTime();
        if(this.isMoving && currentTime - this.startTime >= MOVE_DURATION) {
            System.out.println("Time elapsed");
            this.startTime = 0;
            this.isMoving = false;
        }
        sync();
    }

    public void moveFace(Direction direction, boolean reverse) {
        if(this.isMoving) {
            return;
        }

        this.isMoving = true;
        this.startTime = level.getGameTime();
        sync();
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(this.isMoving) {
            System.out.println("Moving");
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rubiks_cube.clockwise", false));
            return PlayState.CONTINUE;
        }
        else {
            System.out.println("Not moving");
            event.getController().setAnimation(null);
            event.getController().markNeedsReload();
            return PlayState.STOP;
        }
    }

    private void sync() {
        if(!level.isClientSide()) {
            setChanged();
            BlockState state = getBlockState();
            level.sendBlockUpdated(getBlockPos(), state, state, 3);
        }
    }
}
