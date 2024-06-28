package com.example.aircraftwar2024.prop;


import com.example.aircraftwar2024.aircraft.HeroAircraft;

public class BloodPlusProp extends BaseProp {
    private int add_blood;


    // 方便从外界设定所加的血量
    public BloodPlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public int get_AddBlood() {
        return add_blood;
    }

    @Override
    public void work(HeroAircraft hero, boolean usemusic) {
        this.add_blood = (int)(hero.getMaxHp() *0.5);
        hero.decreaseHp(-add_blood);
    }
}


