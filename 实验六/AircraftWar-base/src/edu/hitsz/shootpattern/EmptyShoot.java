package edu.hitsz.shootpattern;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

public class EmptyShoot implements Strategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft, int direction, int power,int shootNum) {
        return new LinkedList<>();
    }
}
