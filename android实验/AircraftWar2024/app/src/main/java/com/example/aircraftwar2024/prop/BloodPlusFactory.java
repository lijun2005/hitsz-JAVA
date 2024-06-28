package com.example.aircraftwar2024.prop;


    
public class BloodPlusFactory implements PropFactory{
    @Override
    public BloodPlusProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BloodPlusProp(locationX, locationY, speedX, speedY);
    }
}

