package com.github.klima7.common.entity;

import com.github.klima7.common.domain.operation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class AbstractRubiksCubeBlockEntity extends BlockEntity implements IAnimatable {

    public static final String IDLE_ANIMATION_NAME = "animation.rubiks_cube.nothing";

    private final String controllerName;
    private final AnimationFactory factory = new AnimationFactory(this);

    protected Operation operation;
    protected long startTime;

    public AbstractRubiksCubeBlockEntity(BlockPos pos, BlockState state, BlockEntityType blockEntity, String controllerName) {
        super(blockEntity, pos, state);
        this.controllerName = controllerName;
    }

    protected abstract void applyOperation(Operation operation);

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, controllerName, 0, this::predicate));
        data.setResetSpeedInTicks(0);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
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
        if(this.isExecutingOperation() && currentTime - this.startTime >= operation.getDuration()) {
            applyOperation(this.operation);
            this.operation = null;
            this.startTime = 0;
        }
        sync();
    }

    public void executeOperation(Operation operation) {
        if(this.isExecutingOperation())
            return;

        if(operation.isInstant()) {
            applyOperation(operation);
        } else {
            this.operation = operation;
            this.startTime = level.getGameTime();
        }

        SoundEvent soundEvent = operation.getSoundEvent();
        if(soundEvent != null) {
            level.playSound(null, getBlockPos(), soundEvent, SoundSource.BLOCKS, 1.0f, 1.0f);
        }

        sync();
    }

    public Direction getFacing() {
        if(operation != null) {
            return operation.getFacing();
        } else {
            return Direction.NORTH;
        }
    }

    public boolean isExecutingOperation() {
        return this.operation != null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationController controller = event.getController();

        if(isExecutingOperation()) {
            controller.easingType = operation.getEasing();
            event.getController().setAnimation(new AnimationBuilder().addAnimation(operation.getAnimationName()));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(IDLE_ANIMATION_NAME, true));
        }
        return PlayState.CONTINUE;
    }

    private void sync() {
        if(level != null && !level.isClientSide()) {
            setChanged();
            BlockState state = getBlockState();
            level.sendBlockUpdated(getBlockPos(), state, state, 3);
        }
    }

}
