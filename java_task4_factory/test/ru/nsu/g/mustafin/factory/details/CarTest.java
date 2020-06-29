package ru.nsu.g.mustafin.factory.details;

import org.junit.Test;

import static org.junit.Assert.*;

public class CarTest {

    @Test
    public void testToString() {
        Car car = new Car(new Accessory(), new Body(), new Motor());

        assertEquals("Auto 4001 (Body: 2001, Motor: 3001, Accessory: 1001)", car.toString());
    }
}