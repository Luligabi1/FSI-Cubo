package com.github.klima7.domain.operation.move;

import com.github.klima7.core.init.SoundRegistry;
import com.github.klima7.domain.cube.locations.OnCubeLocation;
import com.github.klima7.domain.cube.stickers.CubeStickers;
import com.github.klima7.domain.cube.stickers.Sticker;
import com.github.klima7.domain.operation.Operation;
import com.github.klima7.domain.operation.OperationDirection;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;

import java.util.List;

public class Move extends Operation {

    public static final String TYPE = "move";
    private static final String ANIMATION_CLOCKWISE = "animation.rubiks_cube.move.clockwise";
    private static final String ANIMATION_COUNTERCLOCKWISE = "animation.rubiks_cube.move.counterclockwise";
    public static final int DURATION_IN_TICKS = 18;

    private final MoveFace moveFace;
    private final OperationDirection moveDirection;

    public boolean hasAnimation = true;

    public Move(MoveFace moveFace, OperationDirection moveDirection) {
        this.moveFace = moveFace;
        this.moveDirection = moveDirection;
    }

    public MoveFace getMoveFace() {
        return moveFace;
    }

    public OperationDirection getMoveDirection() {
        return moveDirection;
    }

    @Override
    public boolean hasAnimation() {
        return hasAnimation;
    }

    @Override
    public void execute(CubeStickers cubeStickers) {
        if(moveDirection == OperationDirection.CLOCKWISE) {
            moveClockwise(cubeStickers, moveFace);
        } else {
            moveCounterclockwise(cubeStickers, moveFace);
        }
    }

    @Override
    public String getAnimationName() {
        return getMoveDirection() == OperationDirection.CLOCKWISE ? ANIMATION_CLOCKWISE : ANIMATION_COUNTERCLOCKWISE;
    }

    @Override
    public Direction getFacing() {
        return getMoveFace().getDirection();
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundRegistry.MOVE.get();
    }

    @Override
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", TYPE);
        tag.putString("moveFace", moveFace.name());
        tag.putString("moveDirection", moveDirection.name());
        return tag;
    }

    @Override
    public int getDuration() {
        return DURATION_IN_TICKS;
    }

    public static Move load(CompoundTag tag) {
        MoveFace moveFace = MoveFace.valueOf(tag.getString("moveFace"));
        OperationDirection moveDirection = OperationDirection.valueOf(tag.getString("moveDirection"));
        return new Move(moveFace, moveDirection);
    }

    @Override
    public List<Direction> getRequiredFreeDirections() {
        return Direction.stream()
                .filter(direction -> direction.getAxis() != moveFace.getDirection().getAxis() && direction != Direction.DOWN)
                .toList();
    }

    private static void moveClockwise(CubeStickers cubeStickers, MoveFace moveFace) {
        moveMainFaceClockwise(cubeStickers, moveFace);
        moveSideFacesClockwise(cubeStickers, moveFace);
    }

    private static void moveCounterclockwise(CubeStickers cubeStickers, MoveFace moveFace) {
        for(int i=0; i<3; i++) {
            moveClockwise(cubeStickers, moveFace);
        }
    }

    private static void moveMainFaceClockwise(CubeStickers cubeStickers, MoveFace moveFace) {
        rotateFaceClockwise(cubeStickers.getFaceStickers(moveFace.getDirection()));
    }

    private static void moveSideFacesClockwise(CubeStickers cubeStickers, MoveFace moveFace) {
        CubeStickers tmp = CubeStickers.copyOf(cubeStickers);

        for(int face_index=0; face_index<4; face_index++) {
            for(int sticker_index=0; sticker_index<3; sticker_index++) {
                OnCubeLocation changePos = moveFace.getSideStickersLocations((face_index + 1) % 4).getStickerLocation(sticker_index);
                Sticker sticker = tmp.getSticker(moveFace.getSideStickersLocations(face_index).getStickerLocation(sticker_index));
                cubeStickers.setSticker(changePos, sticker);
            }
        }
    }

    @Override
    public String toString() {
        return "Move[" + moveFace + ", " + moveDirection + "]";
    }
}