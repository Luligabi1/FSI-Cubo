package com.github.klima7.core.network;

import com.github.klima7.common.entity.RubiksCubeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundUpdateRubiksCubePacket {

    private final BlockPos pos;
    private final Direction direction;
    private final boolean reverse;

    public ServerboundUpdateRubiksCubePacket(BlockPos pos, Direction direction, boolean reverse) {
        this.pos = pos;
        this.direction = direction;
        this.reverse = reverse;
    }

    public ServerboundUpdateRubiksCubePacket(FriendlyByteBuf buffer) {
        this(
                buffer.readBlockPos(),
                buffer.readEnum(Direction.class),
                buffer.readBoolean()
        );
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.pos);
        buffer.writeEnum(this.direction);
        buffer.writeBoolean(this.reverse);
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
            rcEntity.moveFace(direction, reverse);
        }
    }

}
