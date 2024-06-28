package edu.hitsz.shootpattern;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

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