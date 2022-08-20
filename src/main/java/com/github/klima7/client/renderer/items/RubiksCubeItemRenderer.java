package com.github.klima7.client.renderer.items;

import com.github.klima7.client.model.item.RubiksCubeItemModel;
import com.github.klima7.common.items.RubiksCubeItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RubiksCubeItemRenderer extends GeoItemRenderer<RubiksCubeItem> {
    public RubiksCubeItemRenderer() {
        super(new RubiksCubeItemModel());
    }
}
