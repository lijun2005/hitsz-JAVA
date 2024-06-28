package com.example.aircraftwar2024.prop;

import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.aircraft.HeroAircraft;
import com.example.aircraftwar2024.basic.AbstractFlyingObject;

//与子弹一样，主要初始化一下位置以及飞行方向
public abstract class BaseProp extends AbstractFlyingObject {
    public BaseProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();
        if (locationX <= 0 || locationX >= GameActivity.screenWidth) {
            vanish();
        }
        if (speedY > 0 && locationY >= GameActivity.screenHeight) {
            vanish();
        } else if (locationY <= 0) {
            vanish();
        }
    }

    public abstract void work(HeroAircraft hero, boolean usemusic);
}
