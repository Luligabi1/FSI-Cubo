package com.github.klima7.client.model.item;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.item.RubiksCubeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RubiksCubeItemModel extends AnimatedGeoModel<RubiksCubeItem> {

    @Override
    public ResourceLocation getAnimationResource(RubiksCubeItem animatable) {
        return new ResourceLocation(RubiksCubeMod.MODID, "animations/rubiks_cube.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(RubiksCubeItem object) {
        return new ResourceLocation(RubiksCubeMod.MODID, "geo/rubiks_cube.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RubiksCubeItem object) {
        return new ResourceLocation(RubiksCubeMod.MODID+":textures/block/rubiks_cube.png");
    }
}
