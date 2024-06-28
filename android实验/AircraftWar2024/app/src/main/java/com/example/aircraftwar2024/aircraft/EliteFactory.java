package com.example.aircraftwar2024.aircraft;

import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.activity.config;

public class EliteFactory implements EnemyFactory {

    @Override
    public EliteEnemy createEnemy() {

        return new EliteEnemy((int) (Math.random() * (GameActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() *GameActivity.screenHeight* 0.05),
                config.getEliteSpeedx(),
                config.getEliteSpeedy(),
                config.getEliteHp(),20,config.getElitePower());
    }
}
