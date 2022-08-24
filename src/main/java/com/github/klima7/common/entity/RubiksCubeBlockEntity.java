package com.github.klima7.common.entity;

import com.github.klima7.core.init.BlockEntityRegistry;
import com.github.klima7.core.init.PacketHandler;
import com.github.klima7.core.network.ClientboundUpdateRubiksCubePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;
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

    private final AnimationFactory factory = new AnimationFactory(this);

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
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
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

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rubiks_cube.clockwise", true));
        return PlayState.STOP;
    }

    public void tick() {
        PacketHandler.CHANNEL.send(
            PacketDistributor.TRACKING_CHUNK.with(() -> this.level.getChunkAt(this.worldPosition)),
            new ClientboundUpdateRubiksCubePacket(1)
        );
    }
}
