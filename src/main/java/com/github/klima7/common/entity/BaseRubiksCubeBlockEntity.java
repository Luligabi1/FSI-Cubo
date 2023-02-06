package com.github.klima7.common.entity;

import com.github.klima7.core.init.SoundRegistry;
import com.github.klima7.domain.operation.BlockedCheckStrategy;
import com.github.klima7.domain.operation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
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

import java.util.UUID;

public abstract class BaseRubiksCubeBlockEntity extends BlockEntity implements IAnimatable {

    public static final String IDLE_ANIMATION_NAME = "animation.rubiks_cube.nothing";
    public static final long COOLDOWN_TICKS = 1;
    public static final BlockedCheckStrategy BLOCKED_CHECK_STRATEGY = new BlockedCheckStrategy();

    private final String controllerName;
    private final AnimationFactory factory;
    private Operation operation;
    private long startTime;
    private long finishTime;
    protected UUID playerUUID;

    public BaseRubiksCubeBlockEntity(BlockPos pos, BlockState state, BlockEntityType blockEntity, String controllerName) {
        super(blockEntity, pos, state);
        this.controllerName = controllerName;
        this.factory = new AnimationFactory(this);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, controllerName, 0, this::predicate));
        data.setResetSpeedInTicks(0);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLong("startTime", startTime);
        if(playerUUID != null) {
            tag.putUUID("playerUUID", playerUUID);
        }
        if(operation != null) {
            tag.put("operation", operation.save());
        }
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.startTime = tag.getLong("startTime");
        this.playerUUID = tag.contains("playerUUID") ? tag.getUUID("playerUUID") : null;
        this.operation = tag.contains("operation") ? Operation.load(tag.getCompound("operation")) : null;
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
        if(shouldFinishOperation()) {
            finishOperation(operation);
        }
    }

    public void executeOperation(Operation operation, UUID playerUUID) {
        if(isCubeBusy()) {
            return;
        }

        if(BLOCKED_CHECK_STRATEGY.isBlocked(operation, getBlockPos(), level)) {
            level.playSound(null, getBlockPos(), SoundRegistry.BLOCKED.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            level.getPlayerByUUID(playerUUID).displayClientMessage(Component.translatable("block.rubiks_cube.blocking"), true);
            return;
        }

        startOperation(operation, playerUUID);
        playOperationSound(operation);
    }

    public Direction getFacing() {
        if(isExecutingOperation()) {
            return operation.getFacing();
        } else {
            return Direction.NORTH;
        }
    }

    public boolean isExecutingOperation() {
        return operation != null;
    }

    protected void applyOperation(Operation operation) { }

    private boolean isCubeBusy() {
        return isExecutingOperation() || level.getGameTime() - finishTime < COOLDOWN_TICKS;
    }

    private void startOperation(Operation operation, UUID playerUUID) {
        assert level != null;
        this.operation = operation;
        this.startTime = level.getGameTime();
        this.playerUUID = playerUUID;
        sync();
    }

    private void finishOperation(Operation operation) {
        assert level != null;
        applyOperation(operation);
        this.operation = null;
        this.startTime = 0;
        this.playerUUID = null;
        this.finishTime = level.getGameTime();
        sync();
    }

    private void playOperationSound(Operation operation) {
        assert level != null;
        SoundEvent soundEvent = operation.getSoundEvent();
        if(soundEvent != null) {
            level.playSound(null, getBlockPos(), soundEvent, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    private boolean shouldFinishOperation() {
        return isExecutingOperation() && currentOperationTimeElapsed();
    }

    private boolean currentOperationTimeElapsed() {
        assert level != null;
        long currentTime = level.getGameTime();
        return currentTime - startTime >= operation.getDuration();
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationController<?> controller = event.getController();

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
