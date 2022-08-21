package com.github.klima7.common.domain;

import java.util.Arrays;
import java.util.EnumMap;

public class CubeState {
    private static final StickerColor[] FACES_ORDER = {
            StickerColor.WHITE,
            StickerColor.YELLOW,
            StickerColor.BLUE,
            StickerColor.GREEN,
            StickerColor.RED,
            StickerColor.ORANGE
    };

    private final EnumMap<StickerColor, FaceState> faceStates = new EnumMap<>(StickerColor.class);

    public CubeState() {
        for(StickerColor faceColor : StickerColor.values()) {
            faceStates.put(faceColor, new FaceState(faceColor));
        }
    }

    public CubeState(StickerColor[] stickerColors) {
        if(stickerColors.length != 54) {
            throw new IllegalArgumentException("stickerColors length must be 54 for cube");
        }

        int faceNo = 0;
        for(StickerColor faceColor: FACES_ORDER) {
            StickerColor[] stickerColorsForFace = Arrays.copyOfRange(stickerColors, faceNo * 9, (faceNo + 1) * 9);
            FaceState faceState = new FaceState(stickerColorsForFace);
            faceStates.put(faceColor, faceState);
            faceNo ++;
        }
    }

    public static CubeState fromLetters(String stickerColorLetters) {
        StickerColor[] stickerColors = (StickerColor[]) stickerColorLetters.chars()
                .mapToObj(c -> StickerColor.fromLetter((char) c))
                .toArray();
        return new CubeState(stickerColors);
    }

    public String getLetters() {
        StringBuilder builder = new StringBuilder();
        for(StickerColor faceColor : FACES_ORDER) {
            String faceLetters = faceStates.get(faceColor).toString();
            builder.append(faceLetters);
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName()).append("(\n");
        for(StickerColor faceColor : FACES_ORDER) {
            FaceState faceState = faceStates.get(faceColor);
            builder.append(faceColor)
                    .append(": ")
                    .append(faceState.toString())
                    .append("\n");
        }
        builder.append(")");
        return builder.toString();
    }
}
