package com.github.klima7.common.domain;

import net.minecraft.core.Direction;

public enum MoveFace {

    NORTH(
            Direction.NORTH,
            ClockDirection.COUNTERCLOCKWISE,
            new SideStickersLocations(Direction.UP, 2, 1, 0),
            new SideStickersLocations(Direction.WEST, 2, 1, 0),
            new SideStickersLocations(Direction.DOWN, 2, 1, 0),
            new SideStickersLocations(Direction.EAST, 2, 1, 0)
    ),

    SOUTH(
            Direction.SOUTH,
            ClockDirection.COUNTERCLOCKWISE,
            new SideStickersLocations(Direction.UP, 6, 7, 8),
            new SideStickersLocations(Direction.EAST, 6, 7, 8),
            new SideStickersLocations(Direction.DOWN, 6, 7, 8),
            new SideStickersLocations(Direction.WEST, 6, 7, 8)
    ),

    EAST(
            Direction.EAST,
            ClockDirection.COUNTERCLOCKWISE,
            new SideStickersLocations(Direction.UP, 8, 5, 2),
            new SideStickersLocations(Direction.NORTH, 8, 5, 2),
            new SideStickersLocations(Direction.DOWN, 0, 3, 6),
            new SideStickersLocations(Direction.SOUTH, 8, 5, 2)
    ),

    WEST(
            Direction.WEST,
            ClockDirection.COUNTERCLOCKWISE,
            new SideStickersLocations(Direction.UP, 0, 3, 6),
            new SideStickersLocations(Direction.SOUTH, 0, 3, 6),
            new SideStickersLocations(Direction.DOWN, 8, 5, 2),
            new SideStickersLocations(Direction.NORTH, 0, 3, 6)
    ),

    UP(
            Direction.UP,
            ClockDirection.COUNTERCLOCKWISE,
            new SideStickersLocations(Direction.NORTH, 6, 7, 8),
            new SideStickersLocations(Direction.EAST, 0, 3, 6),
            new SideStickersLocations(Direction.SOUTH, 2, 1, 0),
            new SideStickersLocations(Direction.WEST, 8, 5, 2)
            ),

    DOWN(
            Direction.DOWN,
            ClockDirection.COUNTERCLOCKWISE,
            new SideStickersLocations(Direction.NORTH, 2, 1, 0),
            new SideStickersLocations(Direction.WEST, 0, 3, 6),
            new SideStickersLocations(Direction.SOUTH, 6, 7, 8),
            new SideStickersLocations(Direction.EAST, 8, 5, 2)
    );

    private final Direction direction;
    private final ClockDirection moveDirection;
    private final SideStickersLocations[] sideStickersLocations;

    MoveFace(Direction direction, ClockDirection moveDirection, SideStickersLocations sideStickersLocations1,
             SideStickersLocations sideStickersLocations2, SideStickersLocations sideStickersLocations3,
             SideStickersLocations sideStickersLocations4) {
        this.direction = direction;
        this.moveDirection = moveDirection;
        this.sideStickersLocations = new SideStickersLocations[] {sideStickersLocations1, sideStickersLocations2,
                sideStickersLocations3, sideStickersLocations4};
    }

    public static MoveFace fromDirection(Direction direction) {
        for(MoveFace moveFace : MoveFace.values()) {
            if(moveFace.getDirection() == direction) {
                return moveFace;
            }
        }
        throw new IllegalArgumentException("Unable to find MoveFace with given direction");
    }

    public Direction getDirection() {
        return this.direction;
    }

    public ClockDirection getMoveDirection() {
        return this.moveDirection;
    }

    public SideStickersLocations getSideStickersLocations(int index) {
        return this.sideStickersLocations[index];
    }

}
