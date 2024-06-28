package com.example.aircraftwar2024.shoot;

import com.example.aircraftwar2024.aircraft.AbstractAircraft;
import com.example.aircraftwar2024.aircraft.HeroAircraft;
import com.example.aircraftwar2024.bullet.BaseBullet;
import com.example.aircraftwar2024.bullet.EnemyBullet;
import com.example.aircraftwar2024.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShoot implements Strategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft, int direction, int power, int shootNum) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = 0;
        double speedAll = direction * 5;
        double angle = 0.0;
        int j = 0;
        BaseBullet bullet;
        if (aircraft instanceof HeroAircraft) {
            for (int i = 0; i < shootNum; i++) {
                j = i - shootNum / 2;
                angle = 60.0 * j;
                speedX = (int) (speedAll * Math.sin(Math.toRadians(angle)));
                speedY = (int) (speedAll * Math.cos(Math.toRadians(angle))) +
                        aircraft.getSpeedY();

                bullet = new HeroBullet(x, y, speedX, speedY,
                        power);
                res.add(bullet);
            }

        }
        else {
            for (int i = 0; i < shootNum; i++) {
                j = i - shootNum / 2;
                angle = 60.0 * j;
                speedX = (int) (speedAll * Math.sin(Math.toRadians(angle)));
                speedY = (int) (speedAll * Math.cos(Math.toRadians(angle))) +
                        aircraft.getSpeedY();

                bullet = new EnemyBullet(x, y, speedX, speedY,
                        power);
                res.add(bullet);
            }
        }

        return res;
    }
}

