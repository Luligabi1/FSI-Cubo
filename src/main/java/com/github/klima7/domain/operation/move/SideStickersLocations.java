package com.github.klima7.domain.operation.move;

import com.github.klima7.domain.cube.locations.OnCubeLocation;
import net.minecraft.core.Direction;

public class SideStickersLocations {

    private final Direction direction;
    private final int[] indexes;

    public SideStickersLocations(Direction direction, int index1, int index2, int index3) {
        this.direction = direction;
        this.indexes = new int[] {index1, index2, index3};
    }

    public OnCubeLocation getStickerLocation(int index) {
        return new OnCubeLocation(this.direction, indexes[index]);
    }

}
