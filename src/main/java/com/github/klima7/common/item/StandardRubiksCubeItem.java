package com.github.klima7.common.item;

import com.github.klima7.client.renderer.item.StandardRubiksCubeItemRenderer;
import com.github.klima7.core.init.BlockRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public class StandardRubiksCubeItem extends BaseRubiksCubeItem {

    private static final String CONTROLLER_NAME = "rubiks_cube_item_controller";

    public StandardRubiksCubeItem() {
        super(BlockRegistry.STANDARD_RUBIKS_CUBE.get(), CONTROLLER_NAME);
    }

    @Override
    protected BlockEntityWithoutLevelRenderer getRenderer() {
        return new StandardRubiksCubeItemRenderer();
    }

}
