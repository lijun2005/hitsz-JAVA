package edu.hitsz.shootpattern;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import java.util.List;


public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<BaseBullet> executeStrategy(AbstractAircraft aircraft,int direction,int power,int shootNum) {
        return strategy.shoot(aircraft, direction, power,shootNum);
    }
}
