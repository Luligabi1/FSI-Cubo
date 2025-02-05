package com.github.klima7;

import com.github.klima7.core.init.BlockEntityRegistry;
import com.github.klima7.core.init.BlockRegistry;
import com.github.klima7.core.init.ItemRegistry;
import com.github.klima7.core.init.SoundRegistry;
import com.github.klima7.core.misc.RubiksCubeData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RubiksCubeMod.MODID)
public class RubiksCubeMod {

    public static final String MODID = "rubiks_cube";

    public RubiksCubeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockRegistry.register(modEventBus);
        BlockEntityRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);
        SoundRegistry.register(modEventBus);
    }

}