package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.config;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.threadprocess.MusicThread;
import observerMethod.Observer;

import java.util.LinkedList;
import java.util.List;

public class BombProp extends BaseProp {
    private  List<BaseBullet> enemyBullet;
    private List<AbstractEnemyAircraft> enemyAircrafts;
    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        bgm = new MusicThread("src/videos/bomb_explosion.wav", 1);
        enemyBullet = new LinkedList<>();
        enemyAircrafts = new LinkedList<>();
    }
    public  void addBullets(List<BaseBullet> bullets){
        for (BaseBullet bullet:bullets){
            if (bullet.notValid()) {
                continue;
            }
            else{
                enemyBullet.add(bullet);
            }
        }
    }

    public void addAircrafts(List<AbstractEnemyAircraft> abstractEnemyAircrafts){
        for(AbstractEnemyAircraft aircraft:abstractEnemyAircrafts){
            if (aircraft.notValid()){
                continue;
            }
            else{
                enemyAircrafts.add(aircraft);
            }
        }
    }

    public  void notify_update(){
        for (BaseBullet bullet:enemyBullet){
            bullet.update();
        }
        for (AbstractEnemyAircraft aircraft:enemyAircrafts){
            aircraft.update();
            if(aircraft.notValid()){
                config.score+=aircraft.getScore();
            }
        }
    }
    @Override
    public void work(HeroAircraft hero, boolean usemusic) {
        if (usemusic)
            bgm.start();
        System.out.println("BombSupply active!");
        notify_update();
    }
}
