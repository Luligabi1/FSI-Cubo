package com.github.klima7.common.domain;

enum Sticker {
    WHITE('w', new Color(255, 255, 255)),
    YELLOW('y', new Color(255, 255, 0)),
    RED('r', new Color(255, 0, 0)),
    ORANGE('o', new Color(255, 128, 0)),
    BLUE('b', new Color(0, 0, 255)),
    GREEN('g', new Color(0, 255, 0));

    private final Color color;
    private final char letter;

    Sticker(char letter, Color color) {
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

    public Color getColor() {
        return color;
    }
}
