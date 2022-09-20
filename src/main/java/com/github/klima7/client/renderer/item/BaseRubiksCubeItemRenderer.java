package com.github.klima7.client.renderer.item;

import com.github.klima7.client.model.item.BaseRubiksCubeItemModel;
import com.github.klima7.common.item.BaseRubiksCubeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public abstract class BaseRubiksCubeItemRenderer<T extends BaseRubiksCubeItem> extends GeoItemRenderer<T> {

    public BaseRubiksCubeItemRenderer(AnimatedGeoModel<T> itemModel) {
        super(itemModel);
    }

    @Override
    public ResourceLocation getTextureLocation(T instance) {
        BaseRubiksCubeItemModel rubiksCubeItemModel = (BaseRubiksCubeItemModel) modelProvider;
        return rubiksCubeItemModel.getTextureResource(currentItemStack);
    }

}
