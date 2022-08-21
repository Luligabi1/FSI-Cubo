package com.github.klima7.common.domain;

public class Color {
    private final int red;
    private final int green;
    private final int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getHex() {
        return 255 << 24 | red << 16 | green << 8 | blue;
    }

    @Override
    public String toString() {
        return "Color(" + this.red + ", " + this.green + ", " + this.blue + ")";
    }
}
