package com.example.aircraftwar2024.prop;


import com.example.aircraftwar2024.activity.config;
import com.example.aircraftwar2024.aircraft.HeroAircraft;

public class ScoreProp extends  BaseProp{
    private int add_score;
    public ScoreProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

        this.add_score = 30;
    }
    @Override
    public void work(HeroAircraft hero, boolean usemusic) {
        config.score += this.add_score;
    }
}
