package com.example.aircraftwar2024.aircraft;

import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.activity.config;
import com.example.aircraftwar2024.bullet.BaseBullet;
import com.example.aircraftwar2024.shoot.Context;
import com.example.aircraftwar2024.shoot.DirectShoot;

import java.util.List;

/**
 * 英雄飞机，游戏玩家操控，遵循单例模式（singleton)
 * 【单例模式】
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    private  volatile  static HeroAircraft heroAircraft;

    private int shootNum = 1;
    private int power = config.getHeroPower();
    private int direction = -1;
    public Context context;
    public Thread shootthread;
    public boolean exist_shootthread = false;
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.context = new Context(new DirectShoot());
    }

    public static HeroAircraft getInstance() {
        if (heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft(GameActivity.screenWidth / 2,
                            GameActivity.screenHeight - ImageManager.HERO_IMAGE.getHeight(), 0, 0, config.getHeroHp());
                }
            }
        }
        else{
            System.out.println("不能再次调用");
        }
        return heroAircraft;
    }

    @Override
    public void forward() {
    }

    @Override
    public List<BaseBullet> shoot() {
        return context.executeStrategy(this, this.direction, this.power, this.shootNum);
    }

    public void setShootNum(int shootnum) {
        this.shootNum = shootnum;
    }
    public void reset() {
        // 重置飞机的状态
        this.locationX = GameActivity.screenWidth / 2;
        this.locationY = GameActivity.screenHeight  - ImageManager.HERO_IMAGE.getHeight();
        this.hp = this.getMaxHp();
    }
    public static synchronized void resetInstance() {
        if (heroAircraft != null) {
            heroAircraft = null;
        }
    }
}
