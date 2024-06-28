package com.example.aircraftwar2024.shoot;

import com.example.aircraftwar2024.aircraft.AbstractAircraft;
import com.example.aircraftwar2024.aircraft.HeroAircraft;
import com.example.aircraftwar2024.bullet.BaseBullet;
import com.example.aircraftwar2024.bullet.EnemyBullet;
import com.example.aircraftwar2024.bullet.HeroBullet;
import java.util.LinkedList;
import java.util.List;

public class DirectShoot implements Strategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft, int direction,  int power,int shootNum) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + direction * 5;
        BaseBullet bullet;

        if (aircraft instanceof HeroAircraft) {
            for (int i = 0; i < shootNum; i++) {
                bullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
                res.add(bullet);
            }
        } else {
            for (int i = 0; i < shootNum; i++) {
                bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
                res.add(bullet);
            }
        }

        return res;
    }

}
