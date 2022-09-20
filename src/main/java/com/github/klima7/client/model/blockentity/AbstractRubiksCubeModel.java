package com.github.klima7.client.model.blockentity;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.entity.AbstractRubiksCubeBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public abstract class AbstractRubiksCubeModel<T extends AbstractRubiksCubeBlockEntity> extends AnimatedGeoModel<T> {

    public abstract ResourceLocation getTextureResource(T entity);

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(RubiksCubeMod.MODID, "animations/rubiks_cube.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(RubiksCubeMod.MODID, "geo/rubiks_cube.geo.json");
    }

}
