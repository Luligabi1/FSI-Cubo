package com.github.klima7.common.domain;

public enum MoveFace {
    U(
            Sticker.YELLOW,
            new FaceThreeStickers(Sticker.BLUE, 0, 1, 2),
            new FaceThreeStickers(Sticker.RED, 0, 1, 2),
            new FaceThreeStickers(Sticker.GREEN, 0, 1, 2),
            new FaceThreeStickers(Sticker.ORANGE, 0, 1, 2)
    );

    private Sticker MainFace;


    MoveFace(Sticker faceColor, FaceThreeStickers faceStickers1, FaceThreeStickers faceStickers2,
             FaceThreeStickers faceStickers3, FaceThreeStickers faceStickers4) {

    }
}
