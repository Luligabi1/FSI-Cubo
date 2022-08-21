package com.github.klima7.common.domain;

enum Sticker {
    WHITE('w', 0xffffffff),
    YELLOW('y', 0xffffff00),
    RED('r', 0xffff0000),
    ORANGE('o', 0xffff8000),
    BLUE('b', 0xff0000ff),
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
        throw new IllegalArgumentException("Unable to find Sticker with letter '" + letter + "'");
    }

    public char getLetter() {
        return letter;
    }

    public int getColor() {
        return color;
    }
}
