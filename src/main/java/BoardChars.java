package main.java;

public enum BoardChars {
    WALL('X'), SNAKE('@'), FOOD('O'), SPACE('.');


    private Character text;

    BoardChars(Character text) {
        this.text = text;
    }

    public Character charecter() {
        return text;
    }
}
