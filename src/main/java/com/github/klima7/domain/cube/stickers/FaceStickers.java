package com.github.klima7.domain.cube.stickers;

import com.github.klima7.domain.cube.locations.OnFaceLocation;

import java.util.Arrays;

public class FaceStickers {

    public static final int STICKER_PER_ROW_COUNT = 3;
    public static final int STICKERS_COUNT = STICKER_PER_ROW_COUNT * STICKER_PER_ROW_COUNT;

    private final Sticker[] stickers = new Sticker[STICKERS_COUNT];

    public FaceStickers(Sticker sticker) {
        Arrays.fill(stickers, sticker);
    }

    public FaceStickers(Sticker[] stickers) {
        if(stickers.length != STICKERS_COUNT)
            throw new IllegalArgumentException("Length of stickers array must be 9");

        System.arraycopy(stickers, 0, this.stickers, 0, STICKERS_COUNT);
    }

    public static FaceStickers fromText(String text) {
        Sticker[] stickers = text.chars()
                .mapToObj(c -> Sticker.fromLetter((char) c))
                .toArray(Sticker[]::new);
        return new FaceStickers(stickers);
    }

    public static FaceStickers copyOf(FaceStickers faceStickers) {
        return FaceStickers.fromText(faceStickers.toText());
    }

    public String toText() {
        StringBuilder builder = new StringBuilder();
        for(Sticker sticker : stickers) {
            builder.append(sticker.getLetter());
        }
        return builder.toString();
    }

    public Sticker getSticker(OnFaceLocation location) {
        return stickers[location.getIndex()];
    }

    public void setSticker(OnFaceLocation location, Sticker sticker) {
        stickers[location.getIndex()] = sticker;
    }

    public boolean isSolved() {
        Sticker desiredSticker = stickers[0];
        for(Sticker sticker : stickers) {
            if(sticker != desiredSticker) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.toText() + ")";
    }

}
