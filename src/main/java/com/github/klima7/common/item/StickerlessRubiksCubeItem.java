package com.github.klima7.common.item;

import com.github.klima7.client.renderer.item.StickerlessRubiksCubeItemRenderer;
import com.github.klima7.core.init.BlockRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public class StickerlessRubiksCubeItem extends AbstractRubiksCubeItem {

    private static final String CONTROLLER_NAME = "stickerless_rubiks_cube_item_controller";

    public StickerlessRubiksCubeItem() {
        super(BlockRegistry.STICKERLESS_RUBIKS_CUBE.get(), CONTROLLER_NAME);
    }

    @Override
    protected BlockEntityWithoutLevelRenderer getRenderer() {
        return new StickerlessRubiksCubeItemRenderer();
    }

}
