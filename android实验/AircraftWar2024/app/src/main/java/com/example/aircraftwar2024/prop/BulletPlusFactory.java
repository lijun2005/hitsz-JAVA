package com.example.aircraftwar2024.prop;

public class BulletPlusFactory implements PropFactory {
    @Override
    public BulletPlusProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BulletPlusProp(locationX, locationY, speedX, speedY);
    }
}
