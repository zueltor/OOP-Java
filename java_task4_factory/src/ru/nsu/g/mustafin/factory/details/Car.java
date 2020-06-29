package ru.nsu.g.mustafin.factory.details;

public class Car implements Detail {
    private Accessory accessory;
    private Body body;
    private Motor motor;
    private static int ID = 4000;
    private int id;

    public Car(Accessory accessory, Body body, Motor motor) {
        this.accessory = accessory;
        this.body = body;
        this.motor = motor;
        id = ++ID;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Auto %d (Body: %d, Motor: %d, Accessory: %d)", this.getID(), body.getID(), motor.getID(), accessory.getID());
    }
}
