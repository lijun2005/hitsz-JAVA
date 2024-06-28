package edu.hitsz.aircraft;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.*;
import edu.hitsz.shootpattern.CircleShoot;
import edu.hitsz.shootpattern.Context;

public class Boss extends AbstractEnemyAircraft {
    private int shootNum = 20;
    private int power ;
    private int direction = 1;
    private int prop_kind;
    private PropFactory propfactory;
    private BaseProp prop = null;
    private static final Random random = new Random();
    private List<BaseProp> props = new LinkedList<>();
    public Context context;

    public Boss(int locationX, int locationY, int speedX, int speedY, int hp, int score,int power) {
        super(locationX, locationY, speedX, speedY, hp, score);
        this.prop_kind = random.nextInt(5);
        this.context = new Context(new CircleShoot());
        this.power = power;
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    // @Override
    @Override
    public List<BaseBullet> shoot() {
        return context.executeStrategy(this, this.direction, this.power, this.shootNum);
    }

    @Override
    public List<BaseProp> generate_prop() {
        int x = this.getLocationX();
        int y = this.getLocationY() + direction;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 5;
        for (int i = 0; i < 3; i++) {
            this.prop_kind = random.nextInt(7);
            switch (this.prop_kind) {
                case 0:
                    propfactory = new BloodFactory();
                    prop = propfactory.createProp(x, y, speedX, (int)(speedY - i*0.5));
                    break;
                case 1:
                    propfactory = new BombFactory();
                    prop = propfactory.createProp(x, y, speedX,(int)(speedY - i*0.5));
                    break;
                case 2:
                    propfactory = new BulletFactory();
                    prop = propfactory.createProp(x, y, speedX,(int)(speedY - i*0.5));
                    break;
                case 3:
                    propfactory = null;
                    break;
                case 4:
                    propfactory = new BulletPlusFactory();
                    prop = propfactory.createProp(x, y, speedX, (int) (speedY - i * 0.5));
                case 5:
                    propfactory = new ScoreFactory();
                    prop = propfactory.createProp(x, y, speedX, speedY);
                    break;
                case 6:
                    propfactory = new BloodPlusFactory();
                    prop = propfactory.createProp(x, y, speedX, speedY);
                    break;
                default:
                    propfactory = null;
                    break;
            }
            props.add(prop);
        }
        return props;
    }

    @Override
    public void update() {

    }
}
