package ru.nsu.g.mustafin.factory.details;

public class Body implements Detail {
    private static int ID = 2000;
    private int id;

    public Body() {
        id = ++ID;
    }

    @Override
    public int getID() {
        return id;
    }
}
