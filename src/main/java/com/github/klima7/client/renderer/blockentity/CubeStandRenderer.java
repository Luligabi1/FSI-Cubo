package com.github.klima7.client.renderer.blockentity;

import com.github.klima7.common.entity.CubeStandBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Blocks;

public class CubeStandRenderer implements BlockEntityRenderer<CubeStandBlockEntity> {

    private final BlockEntityRendererProvider.Context context;

    public CubeStandRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(CubeStandBlockEntity be, float partialTicks, PoseStack stack, MultiBufferSource buffer,
                       int combinedOverlay, int packedLight) {
        final BlockRenderDispatcher dispatcher = this.context.getBlockRenderDispatcher();

        dispatcher.renderSingleBlock(Blocks.GLASS.defaultBlockState(), stack, buffer, combinedOverlay, packedLight,
                net.minecraftforge.client.model.data.ModelData.EMPTY, RenderType.solid());
    }

}
