package com.github.klima7.common.item;

import com.github.klima7.client.renderer.item.StickerlessRubiksCubeItemRenderer;
import com.github.klima7.core.init.BlockRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class StickerlessRubiksCubeItem extends BaseRubiksCubeItem {

    private static final String CONTROLLER_NAME = "stickerless_rubiks_cube_item_controller";

    public StickerlessRubiksCubeItem() {
        super(BlockRegistry.STICKERLESS_RUBIKS_CUBE.get(), CONTROLLER_NAME);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new StickerlessRubiksCubeItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

}
