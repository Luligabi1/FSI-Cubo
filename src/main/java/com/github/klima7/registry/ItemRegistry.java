package com.github.klima7.registry;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.item.RubiksCubeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RubiksCubeMod.MODID);

    public static final RegistryObject<RubiksCubeItem> RUBIKS_CUBE_ITEM = ITEMS.register("rubiks_cube", RubiksCubeItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
