package edu.hitsz.aircraft;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;;

public class MobEnemy extends AbstractEnemyAircraft {
    private BaseProp prop = null;
    private List<BaseProp> props = new LinkedList<>();

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp, score);
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
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
