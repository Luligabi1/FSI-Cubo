package com.github.klima7.client.renderer.item;

import com.github.klima7.client.model.item.AbstractRubiksCubeItemModel;
import com.github.klima7.common.item.AbstractRubiksCubeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public abstract class AbstractRubiksCubeItemRenderer<T extends AbstractRubiksCubeItem> extends GeoItemRenderer<T> {

    public AbstractRubiksCubeItemRenderer(AnimatedGeoModel<T> itemModel) {
        super(itemModel);
    }

    @Override
    public ResourceLocation getTextureLocation(T instance) {
        AbstractRubiksCubeItemModel rubiksCubeItemModel = (AbstractRubiksCubeItemModel) modelProvider;
        return rubiksCubeItemModel.getTextureResource(currentItemStack);
    }

}
