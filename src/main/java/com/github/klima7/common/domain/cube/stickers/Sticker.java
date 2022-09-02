package com.github.klima7.common.domain.cube.stickers;

public enum Sticker {

    WHITE('w', 0xffffffff),
    YELLOW('y', 0xff00ffff),
    RED('r', 0xff0000ff),
    ORANGE('o', 0xff0080ff),
    BLUE('b', 0xffff0000),
    GREEN('g', 0xff00ff00);

    private final char letter;
    private final int color;

    Sticker(char letter, int color) {
        this.letter = letter;
        this.color = color;
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

    public int getColor() {
        return color;
    }

}
