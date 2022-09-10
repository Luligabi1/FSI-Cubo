package com.github.klima7.common.domain.operation;

import com.github.klima7.common.domain.cube.locations.OnFaceLocation;
import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.domain.cube.stickers.FaceStickers;
import com.github.klima7.common.domain.operation.move.Move;
import com.github.klima7.common.domain.operation.rotation.Rotation;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import software.bernie.geckolib3.core.easing.EasingType;

public abstract class Operation {

    public abstract void execute(CubeStickers cubeStickers);

    public abstract String getAnimationName();

    public abstract Direction getFacing();

    public abstract CompoundTag save();

    public abstract int getDuration();

    public static Operation load(CompoundTag tag) {
        if(tag == null) return null;
        String type = tag.getString("type");
        return switch(type) {
            case Move.TYPE -> Move.load(tag);
            case Rotation.TYPE -> Rotation.load(tag);
            default -> throw new RuntimeException();
        };
    }

    public EasingType getEasing() {
        return EasingType.Linear;
    }

    public SoundEvent getSoundEvent() {
        return null;
    }

    public boolean isInstant() {
        return getDuration() == 0;
    }

    protected static void rotateFaceClockwise(FaceStickers faceStickers) {
        FaceStickers original = FaceStickers.copyOf(faceStickers);

        // corners
        copyStickerBetweenFaces(original, faceStickers, 2, 0);
        copyStickerBetweenFaces(original, faceStickers, 8, 2);
        copyStickerBetweenFaces(original, faceStickers, 6, 8);
        copyStickerBetweenFaces(original, faceStickers, 0, 6);

        // edges
        copyStickerBetweenFaces(original, faceStickers, 5, 1);
        copyStickerBetweenFaces(original, faceStickers, 7, 5);
        copyStickerBetweenFaces(original, faceStickers, 3, 7);
        copyStickerBetweenFaces(original, faceStickers, 1, 3);
    }

    protected static void rotateFaceClockwise(FaceStickers faceStickers, int turnsCount) {
        for(int i=0; i<turnsCount%4; i++) {
            rotateFaceClockwise(faceStickers);
        }
    }

    protected static void copyStickerBetweenFaces(FaceStickers srcFace, FaceStickers dstFace, int srcIndex, int dstIndex) {
        dstFace.setSticker(new OnFaceLocation(dstIndex), srcFace.getSticker(new OnFaceLocation(srcIndex)));
    }

    protected static void copyFacesBetweenCubes(CubeStickers srcCube, CubeStickers dstCube, Direction srcDirection, Direction dstDirection) {
        dstCube.setFaceStickers(dstDirection, srcCube.getFaceStickers(srcDirection));
    }

}
