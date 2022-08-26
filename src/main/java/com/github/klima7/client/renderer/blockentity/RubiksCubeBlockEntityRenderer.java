package com.github.klima7.client.renderer.blockentity;

import com.github.klima7.client.model.blockentity.RubiksCubeModel;
import com.github.klima7.client.renderer.texture.RubiksCubeTexture;
import com.github.klima7.client.renderer.texture.RubiksCubeTextureManager;
import com.github.klima7.common.entity.RubiksCubeBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RubiksCubeBlockEntityRenderer extends GeoBlockRenderer<RubiksCubeBlockEntity> {

    private final RubiksCubeTextureManager rubiksCubeTextureManager;

    public RubiksCubeBlockEntityRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new RubiksCubeModel());
        this.rubiksCubeTextureManager = new RubiksCubeTextureManager();
    }

    @Override
    public ResourceLocation getTextureLocation(RubiksCubeBlockEntity entity) {
        RubiksCubeTexture texture = rubiksCubeTextureManager.getTexture(entity.getId());
        texture.updateIfNeeded(entity.getCubeState());
        return texture.getResourceLocation();
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

}
