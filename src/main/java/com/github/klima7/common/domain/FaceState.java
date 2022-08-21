package com.github.klima7.common.domain;

import java.util.Arrays;

public class FaceState {
    private final StickerColor[] stickerColors = new StickerColor[9];

    public FaceState(StickerColor stickerColor) {
        Arrays.fill(stickerColors, stickerColor);
    }

    public FaceState(StickerColor[] stickerColors) {
        if(stickerColors.length != 9) {
            throw new IllegalArgumentException("stickerColors length must be 9 for face");
        }
        System.arraycopy(stickerColors, 0, this.stickerColors, 0, 9);
    }

    public static FaceState fromLetters(String stickerColorLetters) {
        StickerColor[] stickerColors = (StickerColor[]) stickerColorLetters.chars()
                .mapToObj(c -> StickerColor.fromLetter((char) c))
                .toArray();
        return new FaceState(stickerColors);
    }

    public String getLetters() {
        StringBuilder builder = new StringBuilder();
        for(StickerColor stickerColor : stickerColors) {
            builder.append(stickerColor.getLetter());
        }
        return builder.toString();
    }

    public StickerColor getSticker(StickerOnFacePos stickerOnFacePos) {
        return stickerColors[stickerOnFacePos.getLinearIndex()];
    }

    public void setSticker(StickerOnFacePos stickerOnFacePos, StickerColor stickerColor) {
        stickerColors[stickerOnFacePos.getLinearIndex()] = stickerColor;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.getLetters() + ")";
    }
}
