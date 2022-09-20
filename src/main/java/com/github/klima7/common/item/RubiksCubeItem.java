package com.github.klima7.common.item;

import com.github.klima7.client.renderer.item.RubiksCubeItemRenderer;
import com.github.klima7.core.init.BlockRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public class RubiksCubeItem extends AbstractRubiksCubeItem {

    private static final String CONTROLLER_NAME = "rubiks_cube_item_controller";

    public RubiksCubeItem() {
        super(BlockRegistry.RUBIKS_CUBE.get(), CONTROLLER_NAME);
    }

    @Override
    protected BlockEntityWithoutLevelRenderer getRenderer() {
        return new RubiksCubeItemRenderer();
    }

}
