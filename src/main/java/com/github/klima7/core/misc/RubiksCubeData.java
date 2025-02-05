package com.github.klima7.core.misc;

import com.github.klima7.RubiksCubeMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class RubiksCubeData extends SavedData {

    public String seed;

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putString("Seed", seed);

        return nbt;
    }

    public static RubiksCubeData createFromNbt(CompoundTag nbt) {
        RubiksCubeData state = new RubiksCubeData();
        state.seed = nbt.getString("Seed");

        return state;
    }

    public static RubiksCubeData create() {
        return new RubiksCubeData();
    }

    public static RubiksCubeData getServerState(MinecraftServer server) {
        DimensionDataStorage persistentStateManager = server.getLevel(Level.OVERWORLD).getDataStorage();

        RubiksCubeData state = persistentStateManager.computeIfAbsent(
            RubiksCubeData::createFromNbt,
            RubiksCubeData::create,
            RubiksCubeMod.MODID
        );

        state.setDirty();

        return state;
    }

}