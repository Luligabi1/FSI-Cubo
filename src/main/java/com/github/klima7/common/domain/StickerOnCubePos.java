package com.github.klima7.common.domain;

public class StickerOnCubePos extends StickerOnFacePos {
    private final StickerColor face;

    public StickerOnCubePos(StickerColor face, StickerOnFacePos stickerOnFacePos) {
        super(stickerOnFacePos.getLinearIndex());
        this.face = face;
    }

    public StickerOnCubePos(StickerColor face, int linearIndex) {
        this(face, new StickerOnFacePos(linearIndex));
    }

    public StickerOnCubePos(StickerColor face, int x, int y) {
        this(face, new StickerOnFacePos(x, y));
    }

    public StickerColor getFace() {
        return this.face;
    }
}
