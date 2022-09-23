package com.github.klima7.core.network;

import com.github.klima7.common.entity.BaseRubiksCubeBlockEntity;
import com.github.klima7.domain.operation.Operation;
import com.github.klima7.domain.operation.OperationDirection;
import com.github.klima7.domain.operation.move.Move;
import com.github.klima7.domain.operation.move.MoveFace;
import com.github.klima7.domain.operation.rotation.Rotation;
import com.github.klima7.domain.operation.rotation.RotationAxis;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class ServerboundUpdateRubiksCubePacket {

    private final BlockPos pos;
    private final UUID playerUUID;
    private final Direction direction;
    private final boolean reverse;
    private final boolean rotation;

    public ServerboundUpdateRubiksCubePacket(BlockPos pos, UUID playerUUID, Direction direction, boolean reverse, boolean rotation) {
        this.pos = pos;
        this.playerUUID = playerUUID;
        this.direction = direction;
        this.reverse = reverse;
        this.rotation = rotation;
    }

    public ServerboundUpdateRubiksCubePacket(FriendlyByteBuf buffer) {
        this(
                buffer.readBlockPos(),
                buffer.readUUID(),
                buffer.readEnum(Direction.class),
                buffer.readBoolean(),
                buffer.readBoolean()
        );
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.pos);
        buffer.writeUUID(this.playerUUID);
        buffer.writeEnum(this.direction);
        buffer.writeBoolean(this.reverse);
        buffer.writeBoolean(this.rotation);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();;
        context.enqueueWork(() -> handleLater(context));
        context.setPacketHandled(true);
    }

    private void handleLater(NetworkEvent.Context context) {
        Level level = Objects.requireNonNull(context.getSender()).level;
        BlockEntity entity = level.getBlockEntity(this.pos);
        if(entity instanceof final BaseRubiksCubeBlockEntity rcEntity) {
            Operation operation = createOperation();
            rcEntity.executeOperation(operation, playerUUID);
        }
    }

    private Operation createOperation() {
        return rotation ? createRotation() : createMove();
    }

    private Move createMove() {
        MoveFace moveFace = MoveFace.fromDirection(this.direction);
        OperationDirection direction = this.reverse ? OperationDirection.COUNTERCLOCKWISE : OperationDirection.CLOCKWISE;
        return new Move(moveFace, direction);
    }

    private Rotation createRotation() {
        RotationAxis rotationAxis = RotationAxis.fromAxis(this.direction.getAxis());
        OperationDirection direction = getRotationDirection();
        return new Rotation(rotationAxis, direction);
    }

    private OperationDirection getRotationDirection() {
        OperationDirection direction = this.reverse ? OperationDirection.COUNTERCLOCKWISE : OperationDirection.CLOCKWISE;
        boolean shouldReverseDirection = this.direction.getAxis() != Direction.Axis.Y &&
                this.direction.getAxisDirection() == Direction.AxisDirection.POSITIVE;
        if(shouldReverseDirection) {
            direction = direction.reverse();
        }
        return direction;
    }

}
