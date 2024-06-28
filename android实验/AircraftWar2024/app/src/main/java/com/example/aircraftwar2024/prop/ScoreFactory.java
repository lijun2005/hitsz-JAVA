package com.example.aircraftwar2024.prop;


public class ScoreFactory implements PropFactory {
    @Override
    public ScoreProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new ScoreProp(locationX, locationY, speedX, speedY);
    }
    
}
