package com.github.klima7.client.model.blockentity;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.client.texture.RubiksCubeTexture;
import com.github.klima7.client.texture.RubiksCubeTextureManager;
import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.domain.operation.rotation.InstantRotations;
import com.github.klima7.common.domain.operation.rotation.RotationsSet;
import com.github.klima7.common.entity.RubiksCubeBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RubiksCubeModel extends AnimatedGeoModel<RubiksCubeBlockEntity> {

    private final RubiksCubeTextureManager rubiksCubeTextureManager = RubiksCubeTextureManager.getInstance();

    @Override
    public ResourceLocation getAnimationResource(RubiksCubeBlockEntity animatable) {
        return new ResourceLocation(RubiksCubeMod.MODID, "animations/rubiks_cube.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(RubiksCubeBlockEntity object) {
        return new ResourceLocation(RubiksCubeMod.MODID, "geo/rubiks_cube.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RubiksCubeBlockEntity entity) {
        CubeStickers rotatedStickers = CubeStickers.copyOf(entity.getCubeStickers());
        InstantRotations instantRotations = new InstantRotations(RotationsSet.createFromDirection(entity.getFacing()));
        instantRotations.execute(rotatedStickers);

        RubiksCubeTexture texture = rubiksCubeTextureManager.getTexture(entity.getId());
        texture.updateIfNeeded(rotatedStickers);
        return texture.getResourceLocation();
    }

}
