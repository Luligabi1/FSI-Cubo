package com.github.klima7.common.entity;

import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.domain.operation.Operation;
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
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RubiksCubeBlockEntity extends BlockEntity implements IAnimatable {

    public static final String CONTROLLER_NAME = "rubiks_cube_block_controller";
    public static final String IDLE_ANIMATION_NAME = "animation.rubiks_cube.nothing";

    private final AnimationFactory factory = new AnimationFactory(this);

    private int id;
    private CubeStickers cubeStickers;
    private Operation operation;
    private long startTime;

    public RubiksCubeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RUBIKS_CUBE.get(), pos, state);
        this.cubeStickers = new CubeStickers();
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
        tag.putLong("startTime", startTime);
        tag.putString("cubeState", cubeStickers.toText());
        if(operation != null)
            tag.put("operation", operation.save());
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.id = tag.getInt("id");
        this.startTime = tag.getLong("startTime");
        this.cubeStickers = CubeStickers.fromText(tag.getString("cubeState"));
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
        long currentTime = level.getGameTime();
        if(this.isExecutingOperation() && currentTime - this.startTime >= operation.getDuration()) {
            this.operation.execute(this.cubeStickers);
            this.operation = null;
            this.startTime = 0;
        }
        sync();
    }

    public void executeOperation(Operation operation) {
        if(this.isExecutingOperation())
            return;

        if(operation.isInstant()) {
            operation.execute(this.cubeStickers);
        } else {
            this.operation = operation;
            this.startTime = level.getGameTime();
        }

        sync();
    }

    public int getId() {
        return id;
    }

    public CubeStickers getCubeStickers() {
        return this.cubeStickers;
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
