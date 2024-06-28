package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.config;

public class BossFactory implements EnemyFactory {
    @Override
    public Boss createEnemy() {
        return new Boss((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                config.getBossSpeedx(),
                config.getBossSpeedy(),
                config.getBossHp(), 100,config.getBossPower());
    }

}
