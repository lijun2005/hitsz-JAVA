package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.threadprocess.MusicThread;

public class BloodProp extends BaseProp {
    private int add_blood;
    // 方便从外界设定所加的血量
    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.add_blood = 10;
        bgm = new MusicThread("src/videos/get_supply.wav", 1);
    }

    public int get_AddBlood() {
        return add_blood;
    }

    @Override
    public void work(HeroAircraft hero, boolean usemusic) {
        if (usemusic) bgm.start();
        hero.decreaseHp(-add_blood);
    }
}