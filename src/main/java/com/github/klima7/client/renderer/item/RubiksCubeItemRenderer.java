package com.github.klima7.client.renderer.item;

import com.github.klima7.client.model.item.RubiksCubeItemModel;
import com.github.klima7.common.item.RubiksCubeItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RubiksCubeItemRenderer extends GeoItemRenderer<RubiksCubeItem> {

    public RubiksCubeItemRenderer() {
        super(new RubiksCubeItemModel());
    }

}
