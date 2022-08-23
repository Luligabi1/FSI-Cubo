package com.github.klima7.client.renderer.blockentity;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.client.model.blockentity.RubiksCubeModel;
import com.github.klima7.client.renderer.texture.RubiksCubeTextureManager;
import com.github.klima7.common.entity.RubiksCubeBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RubiksCubeBlockEntityRenderer extends GeoBlockRenderer<RubiksCubeBlockEntity> {

    private final RubiksCubeTextureManager textureManager;

    public RubiksCubeBlockEntityRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new RubiksCubeModel());
        this.textureManager = new RubiksCubeTextureManager(Minecraft.getInstance().getTextureManager());
    }

    @Override
    public ResourceLocation getTextureLocation(RubiksCubeBlockEntity instance) {
        return new ResourceLocation(RubiksCubeMod.MODID+":textures/block/rubiks_cube.png");
//        return textureManager.getTexture(0);    // TODO
    }

    public void render(RubiksCubeBlockEntity tile, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        var modelProvider = getGeoModelProvider();
        GeoModel model = modelProvider.getModel(modelProvider.getModelResource(tile));
        modelProvider.setLivingAnimations(tile, this.getUniqueID(tile));
        stack.pushPose();
        stack.translate(0, 0.01f, 0);
        stack.translate(0.5, 0, 0.5);

        rotateBlock(getFacing(tile), stack);

        RenderSystem.setShaderTexture(0, getTextureLocation(tile));
        Color renderColor = getRenderColor(tile, partialTicks, stack, bufferIn, null, packedLightIn);
        RenderType renderType = getRenderType(tile, partialTicks, stack, bufferIn, null, packedLightIn,
                getTextureLocation(tile));
        render(model, tile, partialTicks, renderType, stack, bufferIn, null, packedLightIn, OverlayTexture.NO_OVERLAY,
                (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f,
                (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);
        stack.popPose();
    }

    @Override
    protected void rotateBlock(Direction facing, PoseStack stack) {
        switch (facing) {
            case SOUTH:
                stack.mulPose(Vector3f.YP.rotationDegrees(180));
                break;
            case WEST:
                stack.mulPose(Vector3f.YP.rotationDegrees(90));
                break;
            case NORTH:
                stack.mulPose(Vector3f.YP.rotationDegrees(0));
                break;
            case EAST:
                stack.mulPose(Vector3f.YP.rotationDegrees(270));
                break;
            case UP:
                stack.mulPose(Vector3f.XP.rotationDegrees(90));
                stack.translate(0, -0.5, -0.5);
                break;
            case DOWN:
                stack.mulPose(Vector3f.XN.rotationDegrees(90));
                stack.translate(0, -0.5, 0.5);
                break;
        }
    }

    private Direction getFacing(RubiksCubeBlockEntity tile) {
        BlockState blockState = tile.getBlockState();
        if (blockState.hasProperty(HorizontalDirectionalBlock.FACING)) {
            return blockState.getValue(HorizontalDirectionalBlock.FACING);
        } else if (blockState.hasProperty(DirectionalBlock.FACING)) {
            return blockState.getValue(DirectionalBlock.FACING);
        } else {
            return Direction.NORTH;
        }
    }

}
