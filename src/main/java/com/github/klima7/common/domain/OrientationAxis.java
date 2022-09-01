package com.github.klima7.common.domain;

import net.minecraft.core.Direction;

public enum OrientationAxis {

    X(
            Direction.Axis.X,
            new Direction[] { Direction.NORTH, Direction.UP, Direction.SOUTH, Direction.DOWN},
            new int[] { 0, 0, 0, 0 }
    ),

    Y(
            Direction.Axis.Y,
            new Direction[] { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST },
            new int[] { 0, 0, 0, 0 }
    ),

    Z(
            Direction.Axis.Z,
            new Direction[] { Direction.EAST, Direction.UP, Direction.WEST, Direction.DOWN },
            new int[] { 0, 0, 0, 0 }
    );

    private final Direction.Axis axis;
    private final Direction[] sideFaces;
    private final int[] sideRotations;

    OrientationAxis(Direction.Axis axis, Direction[] sideFaces, int[] sideRotations) {
        this.axis = axis;
        this.sideFaces = sideFaces;
        this.sideRotations = sideRotations;
    }

    public static OrientationAxis fromAxis(Direction.Axis axis) {
        for(OrientationAxis orientationAxis : OrientationAxis.values()) {
            if(orientationAxis.getAxis() == axis) {
                return orientationAxis;
            }
        }
        throw new IllegalArgumentException("Unable to find OrientationAxis with given axis");
    }

    public Direction.Axis getAxis() {
        return this.axis;
    }

    public Direction getSideFace(int index) {
        return this.sideFaces[index];
    }

    public int getRotation(int index) {
        return this.sideRotations[index];
    }

}
