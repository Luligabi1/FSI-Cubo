package com.github.klima7.common.domain;

public class MoveExecutor {

    public static void move(CubeState cubeState, MoveFace moveFace, MoveDirection moveDirection) {
        if(moveDirection == MoveDirection.CLOCKWISE) {
            clockwiseMove(cubeState, moveFace);
        } else {
            counterclockwiseMove(cubeState, moveFace);
        }
    }

    private static void counterclockwiseMove(CubeState cubeState, MoveFace moveFace) {
        for(int i=0; i<3; i++) {
            clockwiseMove(cubeState, moveFace);
        }
    }

    private static void clockwiseMove(CubeState cubeState, MoveFace moveFace) {
        rotateMainFaceClockwise(cubeState, moveFace);
        rotateSideFacesClockwise(cubeState, moveFace);
    }

    private static void rotateMainFaceClockwise(CubeState cubeState, MoveFace moveFace) {
        FaceState faceState = cubeState.getFaceState(moveFace.getMainFace());
        FaceState tmp = FaceState.fromFaceState(faceState);

        // corners
        copyStickerBetweenFaces(tmp, faceState, 0, 2);
        copyStickerBetweenFaces(tmp, faceState, 2, 8);
        copyStickerBetweenFaces(tmp, faceState, 8, 6);
        copyStickerBetweenFaces(tmp, faceState, 6, 0);

        //edges
        copyStickerBetweenFaces(tmp, faceState, 1, 5);
        copyStickerBetweenFaces(tmp, faceState, 5, 7);
        copyStickerBetweenFaces(tmp, faceState, 7, 3);
        copyStickerBetweenFaces(tmp, faceState, 3, 1);
    }

    private static void copyStickerBetweenFaces(FaceState srcFace, FaceState dstFace, int srcIndex, int dstIndex) {
        dstFace.setSticker(new StickerOnFacePos(dstIndex), srcFace.getSticker(new StickerOnFacePos(srcIndex)));
    }

    private static void rotateSideFacesClockwise(CubeState cubeState, MoveFace moveFace) {
        CubeState tmp = CubeState.fromCubeState(cubeState);

        for(int face_index=0; face_index<4; face_index++) {
            for(int sticker_index=0; sticker_index<3; sticker_index++) {
                StickerOnCubePos changePos = moveFace.getSideStickers((face_index + 1) % 4).getStickerOnCubePos(sticker_index);
                Sticker sticker = tmp.getSticker(moveFace.getSideStickers(face_index).getStickerOnCubePos(sticker_index));
                cubeState.setSticker(changePos, sticker);
            }
        }
    }

}
