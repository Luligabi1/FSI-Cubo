package com.github.klima7.core.init;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.item.StandardRubiksCubeItem;
import com.github.klima7.common.item.StickerlessRubiksCubeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RubiksCubeMod.MODID);

    public static final RegistryObject<StandardRubiksCubeItem> STANDARD_RUBIKS_CUBE =
            ITEMS.register("standard_rubiks_cube", StandardRubiksCubeItem::new);

    public static final RegistryObject<StickerlessRubiksCubeItem> STICKERLESS_RUBIKS_CUBE =
            ITEMS.register("stickerless_rubiks_cube", StickerlessRubiksCubeItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
