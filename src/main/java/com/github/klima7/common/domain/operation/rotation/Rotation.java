package com.github.klima7.common.domain.operation.rotation;

import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.domain.cube.stickers.FaceStickers;
import com.github.klima7.common.domain.operation.Operation;
import com.github.klima7.common.domain.operation.OperationDirection;
import com.github.klima7.core.init.SoundRegistry;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;

public class Rotation extends Operation {

    public static final String TYPE = "rotation";
    private static final String ANIMATION_CLOCKWISE = "animation.rubiks_cube.rotate.clockwise";
    private static final String ANIMATION_COUNTERCLOCKWISE = "animation.rubiks_cube.rotate.counterclockwise";
    public static final int DURATION_IN_TICKS = 20;

    private final RotationAxis rotationAxis;
    private final OperationDirection rotationDirection;

    public Rotation(RotationAxis rotationAxis, OperationDirection rotationDirection) {
        this.rotationAxis = rotationAxis;
        this.rotationDirection = rotationDirection;
    }

    public RotationAxis getRotationAxis() {
        return this.rotationAxis;
    }

    public OperationDirection getRotationDirection() {
        return this.rotationDirection;
    }

    @Override
    public void execute(CubeStickers cubeStickers) {
        if(rotationDirection == OperationDirection.CLOCKWISE) {
            rotateClockwise(cubeStickers, rotationAxis);
        } else {
            rotateCounterclockwise(cubeStickers, rotationAxis);
        }
    }

    @Override
    public String getAnimationName() {
        return getRotationDirection() == OperationDirection.CLOCKWISE ? ANIMATION_CLOCKWISE : ANIMATION_COUNTERCLOCKWISE;
    }

    @Override
    public Direction getFacing() {
        return switch(getRotationAxis()) {
            case X -> Direction.WEST;
            case Y -> Direction.UP;
            case Z -> Direction.NORTH;
        };
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundRegistry.ROTATE.get();
    }

    @Override
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", TYPE);
        tag.putString("rotationAxis", rotationAxis.name());
        tag.putString("rotationDirection", rotationDirection.name());
        return tag;
    }

    @Override
    public int getDuration() {
        return DURATION_IN_TICKS;
    }

    public static Rotation load(CompoundTag tag) {
        RotationAxis rotationAxis = RotationAxis.valueOf(tag.getString("rotationAxis"));
        OperationDirection rotationDirection = OperationDirection.valueOf(tag.getString("rotationDirection"));
        return new Rotation(rotationAxis, rotationDirection);
    }

    private static void rotateClockwise(CubeStickers cubeStickers, RotationAxis rotationAxis) {
        rotateMainFacesClockwise(cubeStickers, rotationAxis);
        rotateSideFacesClockwise(cubeStickers, rotationAxis);
    }

    private static void rotateCounterclockwise(CubeStickers cubeStickers, RotationAxis rotationAxis) {
        for(int i=0; i<3; i++) {
            rotateClockwise(cubeStickers, rotationAxis);
        }
    }

    private static void rotateMainFacesClockwise(CubeStickers cubeStickers, RotationAxis rotationAxis) {
        FaceStickers negativeFace = cubeStickers.getFaceStickers(rotationAxis.getClockwiseFace());
        rotateFaceClockwise(negativeFace);

        FaceStickers positiveFace = cubeStickers.getFaceStickers(rotationAxis.getCounterclockwiseFace());
        rotateFaceClockwise(positiveFace, 3);
    }

    private static void rotateSideFacesClockwise(CubeStickers cubeStickers, RotationAxis rotationAxis) {
        CubeStickers srcCubeStickers = CubeStickers.copyOf(cubeStickers);

        for(int src_face_index=0; src_face_index<4; src_face_index++) {
            int dst_face_index = (src_face_index + 1) % 4;

            Direction src_face = rotationAxis.getSideFace(src_face_index);
            Direction dst_face = rotationAxis.getSideFace(dst_face_index);

            copyFacesBetweenCubes(srcCubeStickers, cubeStickers, src_face, dst_face);
            rotateFaceClockwise(cubeStickers.getFaceStickers(dst_face), rotationAxis.getRotation(dst_face_index));
        }
    }

}
