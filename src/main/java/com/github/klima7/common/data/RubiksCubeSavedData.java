package com.github.klima7.common.data;

import com.github.klima7.client.renderer.texture.RubiksCubeTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class RubiksCubeSavedData extends SavedData {

    public static final String FILE_NAME = "rubiks_cube";

    public byte[] colors = new byte[RubiksCubeTexture.WIDTH * RubiksCubeTexture.HEIGHT];

    public RubiksCubeSavedData() {
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        return null;
    }

}