package com.example.aircraftwar2024.aircraft;


import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.activity.config;


public class BossFactory implements EnemyFactory {
    @Override
    public Boss createEnemy() {
        return new Boss((int) (Math.random() * (GameActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * GameActivity.screenHeight * 0.05),
                config.getBossSpeedx(),
                config.getBossSpeedy(),
                config.getBossHp(), 100,config.getBossPower());
    }

}
