package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.config;
import edu.hitsz.threadprocess.MusicThread;

public class ScoreProp extends  BaseProp{
    private int add_score;
    public ScoreProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        bgm = new MusicThread("src/videos/get_supply.wav", 1);
        this.add_score = 30;
    }
    @Override
    public void work(HeroAircraft hero, boolean usemusic) {
        if (usemusic)
            bgm.start();
        config.score += this.add_score;
    }
}
