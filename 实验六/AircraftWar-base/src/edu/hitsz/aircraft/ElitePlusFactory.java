package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.config;

public class ElitePlusFactory implements EnemyFactory {
    @Override
    public ElitePlusEnemy createEnemy() {
        return new ElitePlusEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                config.getElitePlusSpeedx(),
                config.getElitePlusSpeedy(),
                config.getElitePlusHp(), 40,config.getElitePlusPower());
    }

}
