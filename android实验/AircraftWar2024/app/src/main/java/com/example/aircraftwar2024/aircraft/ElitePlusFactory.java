package com.example.aircraftwar2024.aircraft;

import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.activity.config;

public class ElitePlusFactory implements EnemyFactory {
    @Override
    public ElitePlusEnemy createEnemy() {
        return new ElitePlusEnemy((int) (Math.random() * (GameActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * GameActivity.screenHeight* 0.05),
                config.getElitePlusSpeedx(),
                config.getElitePlusSpeedy(),
                config.getElitePlusHp(), 40,config.getElitePlusPower());
    }

}

