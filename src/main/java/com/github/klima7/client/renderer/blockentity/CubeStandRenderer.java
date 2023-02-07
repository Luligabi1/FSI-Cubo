package com.github.klima7.client.renderer.blockentity;

import com.github.klima7.common.entity.CubeStandBlockEntity;
import com.github.klima7.core.init.BlockRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;

public class CubeStandRenderer implements BlockEntityRenderer<CubeStandBlockEntity> {

    private final BlockEntityRendererProvider.Context context;

    public CubeStandRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(CubeStandBlockEntity be, float partialTicks, PoseStack stack, MultiBufferSource buffer,
                       int combinedOverlay, int packedLight) {
        if(!be.isEmpty()) {
            ItemStack item = be.getCube();
            renderCube(item, stack, buffer, combinedOverlay, packedLight);
        }
    }

    private void renderCube(ItemStack item, PoseStack stack, MultiBufferSource buffer, int combinedOverlay,
                            int packedLight) {
        final BlockRenderDispatcher dispatcher = this.context.getBlockRenderDispatcher();
        float scale = 0.3f;
        float horizontalOffset = 1 / (2 * scale) - 0.5f;
        float verticalOffset = 0.1f;

        stack.pushPose();
        stack.scale(scale, scale, scale);
        stack.translate(horizontalOffset, verticalOffset, horizontalOffset);

        dispatcher.renderSingleBlock(BlockRegistry.STANDARD_RUBIKS_CUBE.get().defaultBlockState(), stack, buffer, combinedOverlay, packedLight,
                net.minecraftforge.client.model.data.ModelData.EMPTY, RenderType.solid());

        stack.popPose();
    }

}
