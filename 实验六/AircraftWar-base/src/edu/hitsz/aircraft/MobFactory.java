package edu.hitsz.aircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.config;

public class MobFactory implements EnemyFactory{
    @Override
    public MobEnemy createEnemy() {
        return new MobEnemy(        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
        config.getMobSpeedx(),
        config.getMobSpeedy(),
        config.getMobHp(),10);
    }
}
