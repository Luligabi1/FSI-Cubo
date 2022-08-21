package com.github.klima7.common.domain;

public class FaceThreeStickers {
    private final StickerColor faceColor;
    private final int sticker1;
    private final int sticker2;
    private final int sticker3;

    public FaceThreeStickers(StickerColor faceColor, int sticker1, int sticker2, int sticker3) {
        this.faceColor = faceColor;
        this.sticker1 = sticker1;
        this.sticker2 = sticker2;
        this.sticker3 = sticker3;
    }

    public StickerColor getFaceColor() {
        return faceColor;
    }

    public int getFirstStickerLinearIndex() {
        return sticker1;
    }

    public int getSecondStickerLinearIndex() {
        return sticker2;
    }

    public int getThirdStickerLinearIndex() {
        return sticker3;
    }
}
