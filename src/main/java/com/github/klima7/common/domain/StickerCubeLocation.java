package com.github.klima7.common.domain;

import net.minecraft.core.Direction;

public class StickerCubeLocation extends StickerFaceLocation {

    private final Direction direction;

    public StickerCubeLocation(Direction direction, StickerFaceLocation stickerFaceLocation) {
        super(stickerFaceLocation.getIndex());
        this.direction = direction;
    }

    public StickerCubeLocation(Direction direction, int index) {
        this(direction, new StickerFaceLocation(index));
    }

    public StickerCubeLocation(Direction direction, int x, int y) {
        this(direction, new StickerFaceLocation(x, y));
    }

    public Direction getDirection() {
        return this.direction;
    }

}
