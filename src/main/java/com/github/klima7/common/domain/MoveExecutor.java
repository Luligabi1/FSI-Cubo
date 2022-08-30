package com.github.klima7.common.domain;

public class MoveExecutor {

    public static void move(CubeStickers cubeStickers, MoveFace moveFace, MoveDirection moveDirection) {
        if(moveDirection == MoveDirection.CLOCKWISE) {
            clockwiseMove(cubeStickers, moveFace);
        } else {
            counterclockwiseMove(cubeStickers, moveFace);
        }
    }

    private static void counterclockwiseMove(CubeStickers cubeStickers, MoveFace moveFace) {
        for(int i=0; i<3; i++) {
            clockwiseMove(cubeStickers, moveFace);
        }
    }

    private static void clockwiseMove(CubeStickers cubeStickers, MoveFace moveFace) {
        rotateMainFaceClockwise(cubeStickers, moveFace);
        rotateSideFacesClockwise(cubeStickers, moveFace);
    }

    private static void rotateMainFaceClockwise(CubeStickers cubeStickers, MoveFace moveFace) {
        FaceStickers faceStickers = cubeStickers.getFaceStickers(moveFace.getDirection());
        FaceStickers tmp = FaceStickers.copyOf(faceStickers);

        // corners
        copyStickerBetweenFaces(tmp, faceStickers, 0, 2);
        copyStickerBetweenFaces(tmp, faceStickers, 2, 8);
        copyStickerBetweenFaces(tmp, faceStickers, 8, 6);
        copyStickerBetweenFaces(tmp, faceStickers, 6, 0);

        //edges
        copyStickerBetweenFaces(tmp, faceStickers, 1, 5);
        copyStickerBetweenFaces(tmp, faceStickers, 5, 7);
        copyStickerBetweenFaces(tmp, faceStickers, 7, 3);
        copyStickerBetweenFaces(tmp, faceStickers, 3, 1);
    }

    private static void copyStickerBetweenFaces(FaceStickers srcFace, FaceStickers dstFace, int srcIndex, int dstIndex) {
        dstFace.setSticker(new StickerFaceLocation(dstIndex), srcFace.getSticker(new StickerFaceLocation(srcIndex)));
    }

    private static void rotateSideFacesClockwise(CubeStickers cubeStickers, MoveFace moveFace) {
        CubeStickers tmp = CubeStickers.copyOf(cubeStickers);

        for(int face_index=0; face_index<4; face_index++) {
            for(int sticker_index=0; sticker_index<3; sticker_index++) {
                StickerCubeLocation changePos = moveFace.getSideStickersLocations((face_index + 1) % 4).getStickerLocation(sticker_index);
                Sticker sticker = tmp.getSticker(moveFace.getSideStickersLocations(face_index).getStickerLocation(sticker_index));
                cubeStickers.setSticker(changePos, sticker);
            }
        }
    }

}
