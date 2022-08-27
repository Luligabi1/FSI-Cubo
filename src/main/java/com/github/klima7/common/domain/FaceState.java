package com.github.klima7.common.domain;

import java.util.Arrays;

public class FaceState {

    private final Sticker[] stickers = new Sticker[9];

    public FaceState(Sticker sticker) {
        Arrays.fill(stickers, sticker);
    }

    public FaceState(Sticker[] stickers) {
        if(stickers.length != 9) {
            throw new IllegalArgumentException("Length of stickers array must be 9");
        }
        System.arraycopy(stickers, 0, this.stickers, 0, 9);
    }

    public static FaceState fromLetters(String stickerColorLetters) {
        Sticker[] stickers = stickerColorLetters.chars()
                .mapToObj(c -> Sticker.fromLetter((char) c))
                .toArray(Sticker[]::new);
        return new FaceState(stickers);
    }

    public static FaceState fromFaceState(FaceState faceState) {
        return FaceState.fromLetters(faceState.getLetters());
    }

    public String getLetters() {
        StringBuilder builder = new StringBuilder();
        for(Sticker sticker : stickers) {
            builder.append(sticker.getLetter());
        }
        return builder.toString();
    }

    public Sticker getSticker(StickerOnFacePos stickerOnFacePos) {
        return stickers[stickerOnFacePos.getLinearIndex()];
    }

    public void setSticker(StickerOnFacePos stickerOnFacePos, Sticker sticker) {
        stickers[stickerOnFacePos.getLinearIndex()] = sticker;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.getLetters() + ")";
    }

}
