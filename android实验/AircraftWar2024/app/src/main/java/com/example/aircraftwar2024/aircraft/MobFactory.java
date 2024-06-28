package com.example.aircraftwar2024.aircraft;
import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.activity.config;


public class MobFactory implements EnemyFactory{
    @Override
    public MobEnemy createEnemy() {
        return new MobEnemy(        (int) (Math.random() * (GameActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
        (int) (Math.random() * GameActivity.screenHeight * 0.05),
        config.getMobSpeedx(),
        config.getMobSpeedy(),
        config.getMobHp(),10);
    }
}
