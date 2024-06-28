package edu.hitsz.threadprocess;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootpattern.CircleShoot;
import edu.hitsz.shootpattern.DirectShoot;
import edu.hitsz.shootpattern.ScatterShoot;

public class ShootThread implements Runnable {
    public long duration;
    public HeroAircraft hero;
    private int prop_kind;
    private boolean running = false;

    public ShootThread(long duration, HeroAircraft hero, int prop_kind) {
        this.duration = duration;
        this.hero = hero;
        this.prop_kind = prop_kind;
        this.running = true;
        hero.exist_shootthread = true;
    }

    @Override
    public void run() {
        this.hero.exist_shootthread = true;
        if (this.prop_kind == 0) {
            hero.setShootNum(3);
            hero.context.setStrategy(new ScatterShoot());
        } else if (this.prop_kind == 1) {
            hero.setShootNum(20);
            hero.context.setStrategy(new CircleShoot());
        } else {
            hero.setShootNum(1);
            hero.context.setStrategy(new DirectShoot());
        }

        try {
            Thread.sleep(this.duration);
        } catch (InterruptedException e) {
            running = false;
        }

        running = false;
        if (!running) {
            hero.setShootNum(1);
            hero.context.setStrategy(new DirectShoot());
            this.hero.exist_shootthread = false;
        }
    }

    public void interrupt() {
        this.running = false;
        this.hero.exist_shootthread = false;
    }
}
