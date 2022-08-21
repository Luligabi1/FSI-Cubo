package com.github.klima7.common.domain;

public class StickerOnFacePos {
    private final int x;
    private final int y;

    public StickerOnFacePos(int linearIndex) {
        if(linearIndex < 0 || linearIndex > 8)
            throw new IllegalArgumentException("Linear index must be in range <0, 8>");
        this.x = linearIndex / 3;
        this.y = linearIndex % 3;
    }

    public StickerOnFacePos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getLinearIndex() {
        return this.y * 3 + this.y;
    }
}
