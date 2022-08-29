package com.github.klima7.common.entity;

import com.github.klima7.common.domain.CubeState;
import com.github.klima7.common.domain.MoveDirection;
import com.github.klima7.common.domain.MoveExecutor;
import com.github.klima7.common.domain.MoveFace;
import com.github.klima7.core.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.easing.EasingType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RubiksCubeBlockEntity extends BlockEntity implements IAnimatable {

    private static final String CONTROLLER_NAME = "rubiks_cube_block_controller";
    private static final int MOVE_DURATION = 20;
    private static final EasingType EASING_TYPE = EasingType.Linear;

    private static final AnimationBuilder ANIMATION_NOTHING =
            new AnimationBuilder().addAnimation("animation.rubiks_cube.nothing");
    private static final AnimationBuilder ANIMATION_CLOCKWISE =
            new AnimationBuilder().addAnimation("animation.rubiks_cube.clockwise");
    private static final AnimationBuilder ANIMATION_COUNTERCLOCKWISE =
            new AnimationBuilder().addAnimation("animation.rubiks_cube.counterclockwise");

    private final AnimationFactory factory = new AnimationFactory(this);

    private int id;
    private boolean isMoving = false;
    private long startTime;
    private CubeState cubeState;

    public RubiksCubeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RUBIKS_CUBE.get(), pos, state);
        this.cubeState = new CubeState();
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        this.id = level.getFreeMapId();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, CONTROLLER_NAME, 0, this::predicate));
        data.setResetSpeedInTicks(0);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("id", id);
        tag.putBoolean("isMoving", isMoving);
        tag.putLong("startTime", startTime);
        tag.putString("cubeState", cubeState.getLetters());
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.id = tag.getInt("id");
        this.isMoving = tag.getBoolean("isMoving");
        this.startTime = tag.getLong("startTime");
        this.cubeState = CubeState.fromLetters(tag.getString("cubeState"));
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
            this.startTime = 0;
            this.isMoving = false;
            MoveExecutor.move(this.cubeState, MoveFace.F, MoveDirection.CLOCKWISE);
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

    public int getId() {
        return id;
    }

    public CubeState getCubeState() {
        return this.cubeState;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationController<E> controller = event.getController();
        controller.easingType = EASING_TYPE;

        if(this.isMoving) {
            event.getController().setAnimation(ANIMATION_CLOCKWISE);
        }
        else {
            event.getController().setAnimation(ANIMATION_NOTHING);
        }
        return PlayState.CONTINUE;
    }

    private void sync() {
        if(!level.isClientSide()) {
            setChanged();
            BlockState state = getBlockState();
            level.sendBlockUpdated(getBlockPos(), state, state, 3);
        }
    }
}
