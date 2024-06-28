package edu.hitsz.prop;

public class BombFactory implements PropFactory{
    @Override
    public BombProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BombProp(locationX, locationY, speedX, speedY);
    }
}
