package com.github.klima7.client.model.tile;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.entities.RubiksCubeTileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RubiksCubeModel extends AnimatedGeoModel<RubiksCubeTileEntity> {

    @Override
    public ResourceLocation getAnimationResource(RubiksCubeTileEntity animatable) {
        return new ResourceLocation(RubiksCubeMod.MODID, "animations/rubiks_cube.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(RubiksCubeTileEntity object) {
        return new ResourceLocation(RubiksCubeMod.MODID, "geo/rubiks_cube.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RubiksCubeTileEntity object) {
        return new ResourceLocation(RubiksCubeMod.MODID+":textures/block/rubiks_cube.png");
    }

}
