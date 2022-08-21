package com.github.klima7.common.domain;

public enum MoveFace {
    U(
            StickerColor.YELLOW,
            new FaceThreeStickers(StickerColor.BLUE, 0, 1, 2),
            new FaceThreeStickers(StickerColor.RED, 0, 1, 2),
            new FaceThreeStickers(StickerColor.GREEN, 0, 1, 2),
            new FaceThreeStickers(StickerColor.ORANGE, 0, 1, 2)
    );

    private StickerColor MainFace;


    MoveFace(StickerColor faceColor, FaceThreeStickers faceStickers1, FaceThreeStickers faceStickers2,
             FaceThreeStickers faceStickers3, FaceThreeStickers faceStickers4) {

    }
}
