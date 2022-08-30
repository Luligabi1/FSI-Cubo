package com.github.klima7.common.domain;

import net.minecraft.core.Direction;

public class SideStickersLocations {

    private final Direction direction;
    private final int[] indexes;

    public SideStickersLocations(Direction direction, int index1, int index2, int index3) {
        this.direction = direction;
        this.indexes = new int[] {index1, index2, index3};
    }

    public StickerCubeLocation getStickerLocation(int index) {
        return new StickerCubeLocation(this.direction, indexes[index]);
    }

}
