package com.example.aircraftwar2024.aircraft;


import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.bullet.BaseBullet;
import com.example.aircraftwar2024.prop.BaseProp;

import java.util.LinkedList;
import java.util.List;

public class MobEnemy extends AbstractEnemyAircraft {
    private BaseProp prop = null;
    private List<BaseProp> props = new LinkedList<>();

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp, score);
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= GameActivity.screenHeight) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }

    @Override
    public List<BaseProp> generate_prop() {
        props.add(prop);
        return props;
    }
    @Override
    public  void update(){
        this.vanish();
    }
}
