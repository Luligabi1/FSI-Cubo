package com.github.klima7.common.domain.cube.stickers;

public enum Sticker {

    WHITE('w'),
    YELLOW('y'),
    RED('r'),
    ORANGE('o'),
    BLUE('b'),
    GREEN('g');

    private final char letter;

    Sticker(char letter) {
        this.letter = letter;
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

}
