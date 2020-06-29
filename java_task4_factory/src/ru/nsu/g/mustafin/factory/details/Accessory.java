package ru.nsu.g.mustafin.factory.details;

public class Accessory implements Detail {
    private static int ID = 1000;
    private int id;

    public Accessory() {
        id = ++ID;
    }

    @Override
    public int getID() {
        return id;
    }
}
