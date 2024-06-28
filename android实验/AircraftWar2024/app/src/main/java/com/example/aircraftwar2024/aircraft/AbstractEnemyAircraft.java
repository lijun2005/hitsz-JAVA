package com.example.aircraftwar2024.aircraft;


import com.example.aircraftwar2024.observerMethod.Observer;
import com.example.aircraftwar2024.prop.BaseProp;

import java.util.List;

public abstract class AbstractEnemyAircraft extends AbstractAircraft implements Observer {
    protected  int score;

    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp);
        this.score = score;
    }

    public abstract List<BaseProp> generate_prop();

    public int getScore() {
        return this.score;
    }
}
