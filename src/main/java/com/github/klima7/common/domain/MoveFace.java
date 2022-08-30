package com.github.klima7.common.domain;

import net.minecraft.core.Direction;

public enum MoveFace {

    NORTH(
            Direction.NORTH,
            new SideStickersLocations(Direction.UP, 0, 1, 2),
            new SideStickersLocations(Direction.EAST, 0, 1, 2),
            new SideStickersLocations(Direction.DOWN, 0, 1, 2),
            new SideStickersLocations(Direction.WEST, 0, 1, 2)
    );

    private final Direction direction;
    private final SideStickersLocations[] sideStickersLocations;

    MoveFace(Direction direction, SideStickersLocations sideStickersLocations1, SideStickersLocations sideStickersLocations2,
             SideStickersLocations sideStickersLocations3, SideStickersLocations sideStickersLocations4) {
        this.direction = direction;
        this.sideStickersLocations = new SideStickersLocations[] {sideStickersLocations1, sideStickersLocations2,
                sideStickersLocations3, sideStickersLocations4};
    }

    public Direction getDirection() {
        return this.direction;
    }

    public SideStickersLocations getSideStickersLocations(int index) {
        return sideStickersLocations[index];
    }

}
