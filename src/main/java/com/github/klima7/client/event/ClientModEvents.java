package com.github.klima7.client.event;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.client.renderer.blockentity.RubiksCubeBlockEntityRenderer;
import com.github.klima7.core.init.BlockEntityRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = RubiksCubeMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {

    private ClientModEvents() {}

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.RUBIKS_CUBE.get(), RubiksCubeBlockEntityRenderer::new);
    }

}
