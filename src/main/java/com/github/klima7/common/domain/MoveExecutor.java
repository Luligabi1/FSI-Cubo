package com.github.klima7.common.domain;

public class MoveExecutor {

    public static void move(CubeStickers cubeStickers, MoveFace moveFace, ClockDirection moveDirection) {
        if(moveDirection == ClockDirection.CLOCKWISE) {
            moveClockwise(cubeStickers, moveFace);
        } else {
            moveCounterclockwise(cubeStickers, moveFace);
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
        FaceStickers face = cubeStickers.getFaceStickers(moveFace.getDirection());
        FaceStickers original = FaceStickers.copyOf(face);

        // corners
        copyStickerBetweenFaces(original, face, 2, 0);
        copyStickerBetweenFaces(original, face, 8, 2);
        copyStickerBetweenFaces(original, face, 6, 8);
        copyStickerBetweenFaces(original, face, 0, 6);

        // edges
        copyStickerBetweenFaces(original, face, 5, 1);
        copyStickerBetweenFaces(original, face, 7, 5);
        copyStickerBetweenFaces(original, face, 3, 7);
        copyStickerBetweenFaces(original, face, 1, 3);
    }

    private static void moveSideFacesClockwise(CubeStickers cubeStickers, MoveFace moveFace) {
        CubeStickers tmp = CubeStickers.copyOf(cubeStickers);

        for(int face_index=0; face_index<4; face_index++) {
            for(int sticker_index=0; sticker_index<3; sticker_index++) {
                StickerCubeLocation changePos = moveFace.getSideStickersLocations((face_index + 1) % 4).getStickerLocation(sticker_index);
                Sticker sticker = tmp.getSticker(moveFace.getSideStickersLocations(face_index).getStickerLocation(sticker_index));
                cubeStickers.setSticker(changePos, sticker);
            }
        }
    }

    private static void copyStickerBetweenFaces(FaceStickers srcFace, FaceStickers dstFace, int srcIndex, int dstIndex) {
        dstFace.setSticker(new StickerFaceLocation(dstIndex), srcFace.getSticker(new StickerFaceLocation(srcIndex)));
    }

}
