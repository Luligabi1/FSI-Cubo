package com.github.klima7.common.domain;

public class Face3Stickers {

    private final Sticker faceColor;
    private final int[] stickers;

    public Face3Stickers(Sticker faceColor, int sticker1, int sticker2, int sticker3) {
        this.faceColor = faceColor;
        this.stickers = new int[] {sticker1, sticker2, sticker3};
    }

    public StickerOnCubePos getStickerOnCubePos(int index) {
        return new StickerOnCubePos(this.faceColor, stickers[index]);
    }

}
