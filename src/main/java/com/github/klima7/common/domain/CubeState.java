package com.github.klima7.common.domain;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Objects;

public class CubeState {

    private static final Sticker[] FACES_ORDER = {
            Sticker.WHITE,
            Sticker.YELLOW,
            Sticker.BLUE,
            Sticker.GREEN,
            Sticker.RED,
            Sticker.ORANGE
    };

    private final EnumMap<Sticker, FaceState> faceStates = new EnumMap<>(Sticker.class);

    public CubeState() {
        for(Sticker faceColor : Sticker.values()) {
            faceStates.put(faceColor, new FaceState(faceColor));
        }
    }

    public CubeState(Sticker[] stickers) {
        if(stickers.length != 54) {
            throw new IllegalArgumentException("stickerColors length must be 54 for cube");
        }

        int faceNo = 0;
        for(Sticker faceColor: FACES_ORDER) {
            Sticker[] stickerColorsForFace = Arrays.copyOfRange(stickers, faceNo * 9, (faceNo + 1) * 9);
            FaceState faceState = new FaceState(stickerColorsForFace);
            faceStates.put(faceColor, faceState);
            faceNo ++;
        }
    }

    public static CubeState fromLetters(String stickerColorLetters) {
        Sticker[] stickers = stickerColorLetters.chars()
                .mapToObj(c -> Sticker.fromLetter((char) c))
                .toArray(Sticker[]::new);
        return new CubeState(stickers);
    }

    public static CubeState fromCubeState(CubeState cubeState) {
        return CubeState.fromLetters(cubeState.getLetters());
    }

    public String getLetters() {
        StringBuilder builder = new StringBuilder();
        for(Sticker faceColor : FACES_ORDER) {
            String faceLetters = faceStates.get(faceColor).getLetters();
            builder.append(faceLetters);
        }
        return builder.toString();
    }

    public void setSticker(StickerOnCubePos stickerOnCubePos, Sticker sticker) {
        getFaceState(stickerOnCubePos.getFace()).setSticker(stickerOnCubePos, sticker);
    }

    public Sticker getSticker(StickerOnCubePos stickerOnCubePos) {
        return getFaceState(stickerOnCubePos.getFace()).getSticker(stickerOnCubePos);
    }

    public FaceState getFaceState(Sticker face) {
        return faceStates.get(face);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName()).append("(\n");
        for(Sticker faceColor : FACES_ORDER) {
            FaceState faceState = faceStates.get(faceColor);
            builder.append(faceColor)
                    .append(": ")
                    .append(faceState.toString())
                    .append("\n");
        }
        builder.append(")");
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        CubeState cubeState = (CubeState) other;
        return this.getLetters().equals(cubeState.getLetters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(faceStates);
    }

}
