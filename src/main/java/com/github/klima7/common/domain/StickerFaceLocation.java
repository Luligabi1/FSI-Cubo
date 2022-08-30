package com.github.klima7.common.domain;

public class StickerFaceLocation {

    private final int x;
    private final int y;

    public StickerFaceLocation(int index) {
        validateIndex(index);

        this.x = index / 3;
        this.y = index % 3;
    }

    public StickerFaceLocation(int x, int y) {
        validateCoordinate(x);
        validateCoordinate(y);

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getIndex() {
        return this.y * 3 + this.x;
    }

    private void validateIndex(int index) {
        if(index < 0 || index >= FaceStickers.STICKERS_COUNT) {
            String message = String.format("Index must be in range <0, %s>", FaceStickers.STICKERS_COUNT - 1);
            throw new IllegalArgumentException(message);
        }
    }

    private void validateCoordinate(int coordinate) {
        if(coordinate < 0 || coordinate >= FaceStickers.STICKER_PER_ROW_COUNT) {
            String message = String.format("Coordinate must be in range <0, %s>", FaceStickers.STICKER_PER_ROW_COUNT - 1);
            throw new IllegalArgumentException(message);
        }
    }

}
