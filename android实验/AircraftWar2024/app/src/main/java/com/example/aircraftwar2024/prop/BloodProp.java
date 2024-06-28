package com.example.aircraftwar2024.prop;


import com.example.aircraftwar2024.aircraft.HeroAircraft;

public class BloodProp extends BaseProp {
    private int add_blood;
    // 方便从外界设定所加的血量
    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.add_blood = 10;
    }

    public int get_AddBlood() {
        return add_blood;
    }

    @Override
    public void work(HeroAircraft hero, boolean usemusic) {
        hero.decreaseHp(-add_blood);
    }
}