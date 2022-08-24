package com.github.klima7.core.init;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.core.network.ServerboundUpdateRubiksCubePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(RubiksCubeMod.MODID, "main"), () -> PROTOCOL_VERSION, 
            PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    private PacketHandler() {}

    public static void init() {
        int index = 0;

        CHANNEL.messageBuilder(ServerboundUpdateRubiksCubePacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerboundUpdateRubiksCubePacket::encode).decoder(ServerboundUpdateRubiksCubePacket::new)
                .consumerMainThread(ServerboundUpdateRubiksCubePacket::handle).add();
    }

}
