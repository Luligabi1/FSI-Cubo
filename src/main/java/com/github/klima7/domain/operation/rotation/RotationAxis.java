package com.github.klima7.domain.operation.rotation;

import net.minecraft.core.Direction;

public enum RotationAxis {

    X(
            Direction.WEST,
            new Direction[] { Direction.NORTH, Direction.UP, Direction.SOUTH, Direction.DOWN},
            new int[] { 2, 0, 0, 2 }
    ),

    Y(
            Direction.UP,
            new Direction[] { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST },
            new int[] { 1, 1, 1, 1 }
    ),

    Z(
            Direction.NORTH,
            new Direction[] { Direction.EAST, Direction.UP, Direction.WEST, Direction.DOWN },
            new int[] { 0, 0, 0, 0 }
    );

    private final Direction clockwiseFace;
    private final Direction[] sideFaces;
    private final int[] sideRotations;

    RotationAxis(Direction clockwiseFace, Direction[] sideFaces, int[] sideRotations) {
        this.clockwiseFace = clockwiseFace;
        this.sideFaces = sideFaces;
        this.sideRotations = sideRotations;
    }

    public static RotationAxis fromAxis(Direction.Axis axis) {
        for(RotationAxis rotationAxis : RotationAxis.values()) {
            if(rotationAxis.getAxis() == axis) {
                return rotationAxis;
            }
        }
        throw new IllegalArgumentException("Unable to find OrientationAxis with given axis");
    }

    public Direction.Axis getAxis() {
        return clockwiseFace.getAxis();
    }

    public Direction getClockwiseFace() {
        return clockwiseFace;
    }

    public Direction getCounterclockwiseFace() {
        return clockwiseFace.getOpposite();
    }

    public Direction getSideFace(int index) {
        return this.sideFaces[index];
    }

    public int getRotation(int index) {
        return this.sideRotations[index];
    }

}
