package com.github.klima7.common.domain;

public class StickerOnCubePos extends StickerOnFacePos {
    private final Sticker face;

    public StickerOnCubePos(Sticker face, StickerOnFacePos stickerOnFacePos) {
        super(stickerOnFacePos.getLinearIndex());
        this.face = face;
    }

    public StickerOnCubePos(Sticker face, int linearIndex) {
        this(face, new StickerOnFacePos(linearIndex));
    }

    public StickerOnCubePos(Sticker face, int x, int y) {
        this(face, new StickerOnFacePos(x, y));
    }

    public Sticker getFace() {
        return this.face;
    }
}
