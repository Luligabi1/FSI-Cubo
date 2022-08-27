package com.github.klima7.common.domain;

public enum MoveFace {

    F(
            Sticker.BLUE,
            new Face3Stickers(Sticker.YELLOW, 0, 1, 2),
            new Face3Stickers(Sticker.RED, 0, 1, 2),
            new Face3Stickers(Sticker.WHITE, 0, 1, 2),
            new Face3Stickers(Sticker.ORANGE, 0, 1, 2)
    );

    private Sticker mainFace;
    private Face3Stickers[] sideFaceStickers;

    MoveFace(Sticker mainFace, Face3Stickers faceStickers1, Face3Stickers faceStickers2,
             Face3Stickers faceStickers3, Face3Stickers faceStickers4) {
        this.mainFace = mainFace;
        this.sideFaceStickers = new Face3Stickers[] { faceStickers1, faceStickers2, faceStickers3, faceStickers4 };
    }

    public Sticker getMainFace() {
        return this.mainFace;
    }

    public Face3Stickers getSideStickers(int index) {
        return sideFaceStickers[index];
    }

}
