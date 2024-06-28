package edu.hitsz.prop;

public class BulletFactory implements PropFactory {
    @Override
    public BulletProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BulletProp(locationX, locationY, speedX, speedY);
    }
    
}
