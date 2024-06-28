package com.example.aircraftwar2024.prop;

import com.example.aircraftwar2024.aircraft.HeroAircraft;
import com.example.aircraftwar2024.threadProcess.ShootThread;

public class BulletProp extends BaseProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void work(HeroAircraft hero, boolean usemusic) {

        if (hero.exist_shootthread) {
            hero.shootthread.interrupt();
        }
        hero.shootthread = new Thread(new ShootThread(5000, hero, 0));
        hero.shootthread.start();
    }

}
