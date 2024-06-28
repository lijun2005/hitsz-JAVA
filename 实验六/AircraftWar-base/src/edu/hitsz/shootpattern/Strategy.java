package edu.hitsz.shootpattern;

import edu.hitsz.bullet.BaseBullet;
import java.util.List;
import edu.hitsz.aircraft.AbstractAircraft;

public interface Strategy {
    public List<BaseBullet> shoot(AbstractAircraft aircraft,int direction,int power,int shootNum);//输入包括飞机对象
    
}
