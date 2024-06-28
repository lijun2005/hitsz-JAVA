package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.config;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.shootpattern.Context;
import edu.hitsz.shootpattern.DirectShoot;
import java.util.List;

public class HeroAircraft extends AbstractAircraft {
    private  static HeroAircraft heroAircraft;

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
                    heroAircraft = new HeroAircraft(Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(), 0, 0, config.getHeroHp());
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
        this.locationX = Main.WINDOW_WIDTH / 2;
        this.locationY = Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight();
        this.hp = this.getMaxHp();
    }
    public static synchronized void resetInstance() {
        if (heroAircraft != null) {
            heroAircraft = null;
        }
    }
}
