package com.github.klima7.common.item;

import com.github.klima7.core.init.BlockRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;

public class CubeStandItem extends BlockItem {

    private static final Properties PROPERTIES = new Properties()
            .tab(CreativeModeTab.TAB_MISC)
            .stacksTo(64);

    public CubeStandItem() {
        super(BlockRegistry.CUBE_STAND.get(), PROPERTIES);
    }
}
