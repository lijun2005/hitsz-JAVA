package com.example.aircraftwar2024.shoot;
import  com.example.aircraftwar2024.bullet.BaseBullet;
import  java.util.List;
import com.example.aircraftwar2024.aircraft.AbstractAircraft;

public interface Strategy {
    public List<BaseBullet> shoot(AbstractAircraft aircraft,int direction,int power,int shootNum);

}

