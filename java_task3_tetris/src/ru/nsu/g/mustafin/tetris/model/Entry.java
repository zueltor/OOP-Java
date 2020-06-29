package ru.nsu.g.mustafin.tetris.model;

public class Entry {
    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public Entry(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name + ":" + value;
    }

}
