package com.example.aircraftwar2024.aircraft;


import com.example.aircraftwar2024.basic.AbstractFlyingObject;
import com.example.aircraftwar2024.bullet.BaseBullet;

import java.util.List;

public abstract class AbstractAircraft extends AbstractFlyingObject {
    protected int maxHp;
    protected int hp;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease) {
        hp -= decrease;
        if (hp <= 0) {
            hp = 0;
            vanish();
        }
        if (hp >= maxHp) {
            hp = maxHp;
        }
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    /**
     * 飞机射击方法，可射击对象必须实现
     *
     * @return
     *         可射击对象需实现，返回子弹
     *         非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot();
}


