package com.github.klima7.client.model.item;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.item.AbstractRubiksCubeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public abstract class AbstractRubiksCubeItemModel<T extends AbstractRubiksCubeItem> extends AnimatedGeoModel<T> {

    public abstract ResourceLocation getTextureResource(ItemStack itemStack);

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(RubiksCubeMod.MODID, "animations/rubiks_cube.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(RubiksCubeMod.MODID, "geo/rubiks_cube.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return null;
    }

}
