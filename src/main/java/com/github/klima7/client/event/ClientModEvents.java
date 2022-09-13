package com.github.klima7.client.event;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.client.KeyInit;
import com.github.klima7.client.ClientMod;
import com.github.klima7.client.renderer.blockentity.RubiksCubeBlockEntityRenderer;
import com.github.klima7.core.init.BlockEntityRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = RubiksCubeMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {

    private ClientModEvents() {}

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        try {
            ClientMod.getInstance().initialize();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.RUBIKS_CUBE.get(), RubiksCubeBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        for(KeyMapping keyMapping : KeyInit.KEY_MAPPINGS) {
            event.register(keyMapping);
        }
    }

}
