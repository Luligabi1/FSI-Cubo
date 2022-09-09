package com.github.klima7.core.network;

import com.github.klima7.common.domain.operation.Operation;
import com.github.klima7.common.domain.operation.OperationDirection;
import com.github.klima7.common.domain.operation.move.Move;
import com.github.klima7.common.domain.operation.move.MoveFace;
import com.github.klima7.common.domain.operation.rotation.Rotation;
import com.github.klima7.common.domain.operation.rotation.RotationAxis;
import com.github.klima7.common.entity.RubiksCubeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundUpdateRubiksCubePacket {

    private final BlockPos pos;
    private final Direction direction;
    private final boolean reverse;
    private final boolean rotation;

    public ServerboundUpdateRubiksCubePacket(BlockPos pos, Direction direction, boolean reverse, boolean rotation) {
        this.pos = pos;
        this.direction = direction;
        this.reverse = reverse;
        this.rotation = rotation;
    }

    public ServerboundUpdateRubiksCubePacket(FriendlyByteBuf buffer) {
        this(
                buffer.readBlockPos(),
                buffer.readEnum(Direction.class),
                buffer.readBoolean(),
                buffer.readBoolean()
        );
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.pos);
        buffer.writeEnum(this.direction);
        buffer.writeBoolean(this.reverse);
        buffer.writeBoolean(this.rotation);
    }

    public boolean handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> handleLater(context.get()));
        context.get().setPacketHandled(true);
        return true;
    }

    private void handleLater(NetworkEvent.Context context) {
        Level level = context.getSender().level;
        BlockEntity entity = level.getBlockEntity(this.pos);
        if(entity instanceof final RubiksCubeBlockEntity rcEntity) {
            Operation operation = createOperation();
            rcEntity.executeOperation(operation);
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
        OperationDirection direction = this.reverse ? OperationDirection.COUNTERCLOCKWISE : OperationDirection.CLOCKWISE;
        boolean patchDirection = this.direction.getAxisDirection() == Direction.AxisDirection.POSITIVE;
        if(patchDirection) {
            direction = direction.reverse();
        }
        return new Rotation(rotationAxis, direction);
    }

}
