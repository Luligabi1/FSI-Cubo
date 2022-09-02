package com.github.klima7.common.domain.cube.locations;

import net.minecraft.core.Direction;

public class OnCubeLocation extends OnFaceLocation {

    private final Direction direction;

    public OnCubeLocation(Direction direction, OnFaceLocation onFaceLocation) {
        super(onFaceLocation.getIndex());
        this.direction = direction;
    }

    public OnCubeLocation(Direction direction, int index) {
        this(direction, new OnFaceLocation(index));
    }

    public OnCubeLocation(Direction direction, int x, int y) {
        this(direction, new OnFaceLocation(x, y));
    }

    public Direction getDirection() {
        return this.direction;
    }

}
