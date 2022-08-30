package com.github.klima7.core.network;

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
    private final boolean isRotation;

    public ServerboundUpdateRubiksCubePacket(BlockPos pos, Direction direction, boolean reverse, boolean isRotation) {
        this.pos = pos;
        this.direction = direction;
        this.reverse = reverse;
        this.isRotation = isRotation;
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
        buffer.writeBoolean(this.isRotation);
    }

    public boolean handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> handleLater(context.get()));
        context.get().setPacketHandled(true);
        return true;
    }

    private void handleLater(NetworkEvent.Context context) {
        Level level = context.getSender().level;
        BlockEntity entity = level.getBlockEntity(this.pos);
        if (entity instanceof final RubiksCubeBlockEntity rcEntity) {
            if (isRotation) {
                System.out.println("Rotation");
                if (this.direction == Direction.NORTH || this.direction == Direction.SOUTH) {
                    System.out.println("Rotate north-south");
                }
                else if (this.direction == Direction.EAST || this.direction == Direction.WEST) {
                    System.out.println("Rotate east-west");
                }
                else if (this.direction == Direction.UP || this.direction == Direction.DOWN) {
                    System.out.println("Rotate up-down");
                }
            } else {
                rcEntity.moveFace(direction, reverse);
            }
        }
    }

}
