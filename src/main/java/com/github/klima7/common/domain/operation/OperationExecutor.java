package com.github.klima7.common.domain.operation;

import com.github.klima7.common.domain.cube.locations.OnCubeLocation;
import com.github.klima7.common.domain.cube.locations.OnFaceLocation;
import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.domain.cube.stickers.FaceStickers;
import com.github.klima7.common.domain.cube.stickers.Sticker;
import com.github.klima7.common.domain.operation.move.MoveFace;
import com.github.klima7.common.domain.operation.rotation.RotationAxis;
import net.minecraft.core.Direction;

public class OperationExecutor {

    public static void move(CubeStickers cubeStickers, MoveFace moveFace, OperationDirection moveDirection) {
        if(moveDirection == OperationDirection.CLOCKWISE) {
            moveClockwise(cubeStickers, moveFace);
        } else {
            moveCounterclockwise(cubeStickers, moveFace);
        }
    }

    public static void rotate(CubeStickers cubeStickers, RotationAxis rotationAxis, OperationDirection rotationDirection) {
        if(rotationDirection == OperationDirection.CLOCKWISE) {
            rotateClockwise(cubeStickers, rotationAxis);
        } else {
            rotateCounterclockwise(cubeStickers, rotationAxis);
        }
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
        FaceStickers mainFace1 = cubeStickers.getFaceStickers(rotationAxis.getMainFace(0));
        FaceStickers mainFace2 = cubeStickers.getFaceStickers(rotationAxis.getMainFace(1));

        rotateFaceClockwise(mainFace1);
        rotateFaceClockwise(mainFace2);
    }

    private static void rotateSideFacesClockwise(CubeStickers cubeStickers, RotationAxis rotationAxis) {
        CubeStickers srcCubeStickers = CubeStickers.copyOf(cubeStickers);

        for(int src_face_index=0; src_face_index<4; src_face_index++) {
            int dst_face_index = (src_face_index + 1) % 4;

            Direction src_face = rotationAxis.getSideFace(src_face_index);
            Direction dst_face = rotationAxis.getSideFace(dst_face_index);

            copyFacesBetweenCubes(srcCubeStickers, cubeStickers, src_face, dst_face);

            for(int i=0; i<rotationAxis.getRotation(src_face_index); i++) {
                rotateFaceClockwise(cubeStickers.getFaceStickers(src_face));
            }
        }
    }

    private static void rotateFaceClockwise(FaceStickers faceStickers) {
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

    private static void copyStickerBetweenFaces(FaceStickers srcFace, FaceStickers dstFace, int srcIndex, int dstIndex) {
        dstFace.setSticker(new OnFaceLocation(dstIndex), srcFace.getSticker(new OnFaceLocation(srcIndex)));
    }

    private static void copyFacesBetweenCubes(CubeStickers srcCube, CubeStickers dstCube, Direction srcDirection, Direction dstDirection) {
        dstCube.setFaceStickers(dstDirection, srcCube.getFaceStickers(srcDirection));
    }

}
