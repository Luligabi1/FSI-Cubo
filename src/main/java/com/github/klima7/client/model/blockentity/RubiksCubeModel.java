package com.github.klima7.client.model.blockentity;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.entity.RubiksCubeBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RubiksCubeModel extends AnimatedGeoModel<RubiksCubeBlockEntity> {

    @Override
    public ResourceLocation getAnimationResource(RubiksCubeBlockEntity animatable) {
        return new ResourceLocation(RubiksCubeMod.MODID, "animations/rubiks_cube.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(RubiksCubeBlockEntity object) {
        return new ResourceLocation(RubiksCubeMod.MODID, "geo/rubiks_cube.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RubiksCubeBlockEntity object) {
        return null;    // texture handled in renderer
    }

}
