package com.github.klima7.common.domain.operation.rotation;

import net.minecraft.core.Direction;

public class RotationsSet {

    private final int turnsX;
    private final int turnsY;
    private final int turnsZ;

    public RotationsSet(int turnsX, int turnsY, int turnsZ) {
        this.turnsX = turnsX;
        this.turnsY = turnsY;
        this.turnsZ = turnsZ;
    }

    public static RotationsSet createToDirection(Direction direction) {
        return switch(direction) {
            case NORTH -> new RotationsSet(0, 0, 0);
            case SOUTH -> new RotationsSet(0, 2, 0);
            case EAST -> new RotationsSet(0, 1, 0);
            case WEST -> new RotationsSet(0, 3, 0);
            case UP -> new RotationsSet(1, 0, 0);
            case DOWN -> new RotationsSet(3, 0, 0);
        };
    }

    public static RotationsSet createFromDirection(Direction direction) {
        return switch(direction) {
            case SOUTH -> new RotationsSet(0, 0, 0);
            case NORTH -> new RotationsSet(0, 2, 0);
            case WEST -> new RotationsSet(0, 1, 0);
            case EAST -> new RotationsSet(0, 3, 0);
            case DOWN -> new RotationsSet(1, 0, 0);
            case UP -> new RotationsSet(3, 0, 0);
        };
    }

    public int getTurnsX() {
        return turnsX;
    }

    public int getTurnsY() {
        return turnsY;
    }

    public int getTurnsZ() {
        return turnsZ;
    }

}
