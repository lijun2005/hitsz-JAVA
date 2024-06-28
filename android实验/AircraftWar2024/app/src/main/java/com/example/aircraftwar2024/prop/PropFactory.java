package com.example.aircraftwar2024.prop;

public interface PropFactory {
    BaseProp createProp(int locationX, int locationY, int speedX, int speedY);
}
