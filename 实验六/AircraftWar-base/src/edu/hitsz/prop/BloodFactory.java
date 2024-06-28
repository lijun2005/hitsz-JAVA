package edu.hitsz.prop;

public class BloodFactory implements PropFactory{
    @Override
    public BloodProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BloodProp(locationX, locationY, speedX, speedY);
    }
}
