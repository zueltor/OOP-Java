package ru.nsu.g.mustafin.factory.details;

public class Motor implements Detail {
    private static int ID = 3000;
    private int id;

    public Motor() {
        id = ++ID;
    }

    @Override
    public int getID() {
        return id;
    }
}
