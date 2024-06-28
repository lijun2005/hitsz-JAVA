package com.example.aircraftwar2024.aircraft;

import com.example.aircraftwar2024.prop.BaseProp;
import com.example.aircraftwar2024.prop.PropFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.bullet.BaseBullet;

import com.example.aircraftwar2024.prop.BloodFactory;

import com.example.aircraftwar2024.prop.BombFactory;
import com.example.aircraftwar2024.prop.BulletFactory;

import com.example.aircraftwar2024.prop.ScoreFactory;

import com.example.aircraftwar2024.shoot.Context;
import com.example.aircraftwar2024.shoot.DirectShoot;

public class EliteEnemy extends AbstractEnemyAircraft {

    private int shootNum = 1;
    private int power;
    private int direction = 1;
    private int prop_kind;
    private PropFactory propfactory;
    private BaseProp prop = null;
    private static final Random random = new Random();
    private List<BaseProp> props = new LinkedList<>();
    public Context context;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int score,int power) {
        super(locationX, locationY, speedX, speedY, hp, score);
        this.prop_kind = random.nextInt(5);
        this.context = new Context(new DirectShoot());
        this.power = power;
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
        return context.executeStrategy(this, this.direction, this.power, this.shootNum);
    }


    @Override
    public List<BaseProp> generate_prop() {
        int x = this.getLocationX();
        int y = this.getLocationY() + direction;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 2;
        switch (this.prop_kind) {
            case 0:
                propfactory = new BloodFactory();
                prop = propfactory.createProp(x, y, speedX, speedY);
                break;
            case 1:
                propfactory = new BombFactory();
                prop = propfactory.createProp(x, y, speedX, speedY);
                break;
            case 2:
                propfactory = new BulletFactory();
                prop = propfactory.createProp(x, y, speedX, speedY);
                break;
            case 3:
                propfactory = null;
                break;
            case 4:
                propfactory = new ScoreFactory();
                prop = propfactory.createProp(x, y, speedX, speedY);
                break;
            default:
                propfactory = null;
                break;
        }
        props.add(prop);
        return props;
    }
    @Override
    public  void update(){
        this.vanish();
    }
}
