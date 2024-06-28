package com.example.aircraftwar2024.bullet;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.basic.AbstractFlyingObject;

import com.example.aircraftwar2024.observerMethod.Observer;

public abstract  class BaseBullet extends AbstractFlyingObject implements  Observer{


    private int power;


    public BaseBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY);
        this.power = power;
    }

    @Override
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= GameActivity.screenWidth) {
            vanish();
        }

        // 判定 y 轴出界
        if (speedY > 0 && locationY >= GameActivity.screenHeight ) {
            // 向下飞行出界
            vanish();
        }else if (locationY <= 0){
            // 向上飞行出界
            vanish();
        }

    }

    public int getPower() {
        return power;
    }

}
