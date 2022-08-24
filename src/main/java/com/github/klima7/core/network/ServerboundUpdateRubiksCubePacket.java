package com.github.klima7.core.network;

import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerboundUpdateRubiksCubePacket {

    private Direction direction;
    private boolean reverse;

    public ServerboundUpdateRubiksCubePacket(Direction direction, boolean reverse) {
        this.direction = direction;
        this.reverse = reverse;
    }

    public ServerboundUpdateRubiksCubePacket(FriendlyByteBuf buffer) {
        this(buffer.readEnum(Direction.class), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeEnum(this.direction);
        buffer.writeBoolean(this.reverse);
    }

    public boolean handle(Supplier<NetworkEvent.Context> context) {
        final var success = new AtomicBoolean(false);
        context.get().enqueueWork(() -> {
            System.out.println("Received update packet");
        });
        context.get().setPacketHandled(true);
        return success.get();
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isReverse() {
        return reverse;
    }

}
