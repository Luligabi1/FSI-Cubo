package com.github.klima7.client.renderer.blockentity;

import com.github.klima7.common.entity.CubeStandBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
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
        final ItemRenderer itemRenderer = context.getItemRenderer();
        final float scale = 0.3f;

        stack.pushPose();
        stack.scale(scale, scale, scale);
        stack.translate(1.65, 1.1, 1.4);

        final LocalPlayer player = Minecraft.getInstance().player;
        itemRenderer.renderStatic(player, item, ItemTransforms.TransformType.FIXED, false, stack, buffer,
                Minecraft.getInstance().level, combinedOverlay, packedLight, 0);

        stack.popPose();
    }

}
