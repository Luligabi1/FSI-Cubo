package com.github.klima7.domain.operation.rotation;

import com.github.klima7.domain.cube.stickers.CubeStickers;
import com.github.klima7.domain.operation.Operation;
import com.github.klima7.domain.operation.OperationDirection;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

public class InstantRotations extends Operation {

    private final RotationsSet rotationsSet;

    public InstantRotations(RotationsSet rotationsSet) {
        this.rotationsSet = rotationsSet;
    }

    @Override
    public boolean hasAnimation() {
        return true;
    }

    @Override
    public void execute(CubeStickers cubeStickers) {
        rotateOnAxis(cubeStickers, RotationAxis.X, rotationsSet.getTurnsX());
        rotateOnAxis(cubeStickers, RotationAxis.Y, rotationsSet.getTurnsY());
        rotateOnAxis(cubeStickers, RotationAxis.Z, rotationsSet.getTurnsZ());
    }

    @Override
    public String getAnimationName() {
        return null;
    }

    @Override
    public Direction getFacing() {
        return null;
    }

    @Override
    public CompoundTag save() {
        return null;
    }

    @Override
    public int getDuration() {
        return 0;
    }

    private void rotateOnAxis(CubeStickers cubeStickers, RotationAxis rotationAxis, int turnsCount) {
        Rotation rotation = new Rotation(rotationAxis, OperationDirection.CLOCKWISE);
        for(int i=0; i < turnsCount % 4; i++) {
            rotation.execute(cubeStickers);
        }
    }
}