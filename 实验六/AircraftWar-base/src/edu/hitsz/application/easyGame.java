package edu.hitsz.application;
import GUI.RankList;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.scorepattern.User;
import edu.hitsz.scorepattern.UserDaoImpl;
import edu.hitsz.threadprocess.MusicThread;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class easyGame extends Game {

    private final HeroAircraft heroAircraft;

    private double probability = 0.;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */


     private Object[] resultEnemyGenerate;
     private Object[] resultEnemyShoot;
     private Object[] resultHeroShoot;


    private final Random rand = new Random();


    public easyGame(boolean usemusic,String mapPath) {
        super(usemusic,mapPath);
        heroAircraft = HeroAircraft.getInstance();
        // bossList = new LinkedList<>();

        // 启动英雄机鼠标监听
        new HeroController(this, heroAircraft);
        userdao = new UserDaoImpl(Paths.get("./score.dat"));
    }


    @Override
    protected void initializeParameter() {
        ImageManager.setBackgroundImage(this.mapPath);
        this.eliteProb = 0.2;
        this.elitePlusProb = 0.1;
        this.cycleDurationEnemyGenerate = 600; //敌机产生周期
        this.cycleDurationEnemyShoot = 600; //敌机射击周期
        this.cycleDurationHeroShoot = 600; //英雄机射击周期
        this.method_kind = 0;
        this.enemyMaxNumber = 3;

        this.heroPower = 30;
        this.heroHp = 1000;
        
        this.mobHp = 30;
        this.mobSpeedx = 0;
        this.mobSpeedy = 10;
    
        this.eliteHp = 30;
        this.eliteSpeedx = 0;
        this.eliteSpeedy = 10;
        this.elitePower = 10;

        this.elitePlusSpeedx = 4;
        this.elitePlusSpeedy = 10;
        this.elitePlusHp = 30;
        this.elitePlusPower = 20;

        config.setMethod_kind(method_kind);
        config.setHeroHp(heroHp);
        config.setHeroPower(heroPower);

        config.setMobHp(mobHp);
        config.setMobSpeedx(mobSpeedx);
        config.setMobSpeedy(mobSpeedy);

        config.setEliteHp(eliteHp);
        config.setEliteSpeedx(eliteSpeedx);
        config.setEliteSpeedy(eliteSpeedy);
        config.setElitePower(elitePower);

        config.setElitePlusHp(elitePlusHp);
        config.setElitePlusPower(elitePlusPower);
        config.setElitePlusSpeedx(elitePlusSpeedx);
        config.setElitePlusSpeedy(elitePlusSpeedy);
    }

    @Override
    protected void changeParameter() {  
    }
    @Override
    protected  void generateEnemyAction() {
        resultEnemyGenerate = timeCountAndNewCycleJudge(EnemyGenerateTime, cycleDurationEnemyGenerate);
        EnemyGenerateTime = (int) resultEnemyGenerate[1];
//        System.out.println(EnemyGenerateTime);
        if (((boolean) resultEnemyGenerate[0])) {
            synchronized (enemyAircrafts) {
                if (enemyAircrafts.size() < enemyMaxNumber) {
                        probability = rand.nextDouble();
                        if (probability <= elitePlusProb) {
                            enemyAircrafts.add(eliteplusFactory.createEnemy());
                        } else if (probability <= elitePlusProb+eliteProb) {
                            enemyAircrafts.add(eliteFactory.createEnemy());
                        } else {
                            enemyAircrafts.add(mobFactory.createEnemy());
                        }
                }
            }
        }
    }
    @Override
    protected  void enemyShootAction() {
        resultEnemyShoot = timeCountAndNewCycleJudge(EnemyShootTime, cycleDurationEnemyShoot);
        EnemyShootTime = (int) resultEnemyShoot[1];
        if (((boolean) resultEnemyShoot[0])) {
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
        }
    }
    @Override
    protected  void heroShootAction() {
        resultHeroShoot = timeCountAndNewCycleJudge(HeroShootTime, cycleDurationHeroShoot);
        HeroShootTime = (int) resultHeroShoot[1];
        if ((boolean) resultHeroShoot[0]) {
            heroBullets.addAll(heroAircraft.shoot());
        }
    }



    @Override
    protected void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }
    
    @Override
    protected  void crashCheckAction() {
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if (this.usemusic) {
                            MusicThread bullmusic = new MusicThread("src/videos/bullet_hit.wav", 1);
                            bullmusic.start();
                        }
                        config.score += enemyAircraft.getScore();
                        List<BaseProp> prop = enemyAircraft.generate_prop();
                        for (BaseProp i : prop) {
                            if (i != null) {
                                props.add(i);
                            }
                        }

                    }
                }

                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        for (BaseProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                if (prop instanceof BombProp) {
                    ((BombProp) prop).addAircrafts(enemyAircrafts);
                    ((BombProp) prop).addBullets(enemyBullets);
                }
                prop.work(heroAircraft, this.usemusic);
                prop.vanish();
            }
        }

    }
    @Override
    protected  void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
        // bossList.removeIf(AbstractFlyingObject::notValid);
    }
    @Override
    protected  void gameoverProcessAction() {
        if (heroAircraft.getHp() <= 0) {
            executorService.shutdown();
            if (all_bgmThread != null)
                all_bgmThread.stopmusic();
            if (boss_gbmThread != null) {
                boss_gbmThread.stopmusic();
            }
            gameOverFlag = true;
            if (this.usemusic)
                gameover_bgmThread.start();
            System.out.println("Game Over!");
            HeroAircraft.resetInstance();
            LocalDateTime dateTime = LocalDateTime.now(); // get the current date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String name = JOptionPane.showInputDialog(null, "游戏结束，你的得分为" + config.score + "。\n" + "请输入用户名保存得分：\n", "输入",
                    JOptionPane.PLAIN_MESSAGE);
            User user = new User(name, config.score, dateTime.format(formatter));
            userdao.AddUser(user);
            userdao.save();
            config.resetToDefault();
            RankList rank = new RankList(method_kind, userdao);
            Main.cardPanel.add(rank.getMainPanel());
            Main.cardLayout.last(Main.cardPanel);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);
        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        // 绘制得分和生命值
        paintScoreAndLife(g);
        // paintBossHp(g);
    }


    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + config.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

}
