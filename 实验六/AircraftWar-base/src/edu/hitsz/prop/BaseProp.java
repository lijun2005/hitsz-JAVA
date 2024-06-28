package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.threadprocess.MusicThread;


//与子弹一样，主要初始化一下位置以及飞行方向
public abstract class BaseProp extends AbstractFlyingObject {
    protected  MusicThread bgm;
    public BaseProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        bgm = new MusicThread("src/videos/get_supply.wav", 1);
    }

    @Override
    public void forward() {
        super.forward();
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }
        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        } else if (locationY <= 0) {
            vanish();
        }
    }

    
    public abstract void work(HeroAircraft hero,boolean usemusic);
}
