package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.threadprocess.MusicThread;
import edu.hitsz.threadprocess.ShootThread;

public class BulletProp extends BaseProp {


    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        bgm = new MusicThread("src/videos/get_supply.wav", 1);
    }

    @Override
    public void work(HeroAircraft hero, boolean usemusic) {
        if (usemusic)
            bgm.start();
        if (hero.exist_shootthread) {
            hero.shootthread.interrupt();
        }
        hero.shootthread = new Thread(new ShootThread(5000, hero, 0));
        hero.shootthread.start();
    }

}
