package com.github.klima7.common.domain.cube.stickers;

public enum Sticker {

    WHITE('w', 0),
    YELLOW('y', 1),
    RED('r', 2),
    ORANGE('o', 3),
    BLUE('b', 4),
    GREEN('g', 5);

    private final char letter;
    private final int textureIndex;

    Sticker(char letter, int textureIndex) {
        this.letter = letter;
        this.textureIndex = textureIndex;
    }

    public static Sticker fromLetter(char letter) {
        for(Sticker sticker : Sticker.values()) {
            if(sticker.letter == letter) {
                return sticker;
            }
        }
        throw new IllegalArgumentException(String.format("Unable to find Sticker with letter '%c'", letter));
    }

    public char getLetter() {
        return letter;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

}
