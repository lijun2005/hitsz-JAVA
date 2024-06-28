package edu.hitsz.application;
import GUI.RankList;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.Boss;
import edu.hitsz.aircraft.BossFactory;
import edu.hitsz.aircraft.EnemyFactory;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.scorepattern.User;
import edu.hitsz.scorepattern.UserDaoImpl;
import edu.hitsz.threadprocess.MusicThread;
import edu.hitsz.threadprocess.ShakeThread;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class hardGame extends Game {

    private final HeroAircraft heroAircraft;

    private final List<AbstractEnemyAircraft> bossList;


    private double probability = 0.;
    private int threshold;
    private int lastBossScore;
    private double numada;
    private double alpha;
    private double beta;
    private double gamma;
    private final EnemyFactory bossFactory = new BossFactory();
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration;
    private int methodTime = 0;

    private Object[] resultEnemyGenerate;
    private Object[] resultEnemyShoot;
    private Object[] resultHeroShoot;
    private Object[] resultmethod;

    private final Random rand = new Random();


    public hardGame(boolean usemusic,String mapPath) {
        super(usemusic,mapPath);
        heroAircraft = HeroAircraft.getInstance();
        bossList = new LinkedList<>();

        // 启动英雄机鼠标监听
        new HeroController(this, heroAircraft);
        userdao = new UserDaoImpl(Paths.get("./hardscore.dat"));
    }


    @Override
    protected void initializeParameter() {
        ImageManager.setBackgroundImage(this.mapPath);
        this.numada = 1.01;
        this.alpha = 0.99;
        this.beta = 1.3;
        this.eliteProb = 0.4;
        this.elitePlusProb = 0.3;
        this.gamma=0.99;
        threshold = 1500;
        lastBossScore = 0;
        this.cycleDurationEnemyGenerate = 600; //敌机产生周期
        this.cycleDurationEnemyShoot = 600; //敌机射击周期
        this.cycleDurationHeroShoot = 600; //英雄机射击周期
        this.cycleDuration = 10000; //每隔10s增大难度为原来的namada倍

        this.method_kind = 2;
        this.enemyMaxNumber = 3;

        this.heroPower = 60;
        this.heroHp = 1000;
        
        this.mobHp = 30;
        this.mobSpeedx = 1;
        this.mobSpeedy = 10;
    
        this.eliteHp = 30;
        this.eliteSpeedx = 1;
        this.eliteSpeedy = 10;
        this.elitePower = 10;

        this.elitePlusSpeedx = 4;
        this.elitePlusSpeedy = 10;
        this.elitePlusHp = 30;
        this.elitePlusPower = 20;

        this.BossHp = 500;
        this.BossPower = 40;
        this.BossSpeedx = 2;
        this.BossSpeedy = 0;

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

        config.setBossHp(BossHp);
        config.setBossPower(BossPower);
        config.setBossSpeedx(BossSpeedx);
        config.setBossSpeedy(BossSpeedy);
    }

    @Override
    protected void changeParameter() {
       resultmethod = timeCountAndNewCycleJudge(methodTime, cycleDuration);
       methodTime = (int) resultmethod[1];
       if ((boolean) resultmethod[0]) {
           System.out.println("游戏难度提升 所有的属性提升"+this.numada+"倍,周期变为原来的"+this.alpha+"倍");
            // 增加难度
            this.eliteProb = Math.min(this.eliteProb * this.numada, 1.0);
            this.elitePlusProb = Math.min(this.elitePlusProb * this.numada, 1.0);

           this.heroPower = (int) Math.round(this.heroPower * this.numada);
           this.heroHp = (int) Math.round(this.heroHp * this.numada);

           this.mobHp = (int) Math.round(this.mobHp * this.numada);
           this.mobSpeedx = (int) Math.round(this.mobSpeedx * this.numada);
           this.mobSpeedy = (int) Math.round(this.mobSpeedy * this.numada);

           this.eliteHp = (int) Math.round(this.eliteHp * this.numada);
           this.eliteSpeedx = (int) Math.round(this.eliteSpeedx * this.numada);
           this.eliteSpeedy = (int) Math.round(this.eliteSpeedy * this.numada);
           this.elitePower = (int) Math.round(this.elitePower * this.numada);

           this.elitePlusHp = (int) Math.round(this.elitePlusHp * this.numada);
           this.elitePlusPower = (int) Math.round(this.elitePlusPower * this.numada);
           this.elitePlusSpeedx = (int) Math.round(this.elitePlusSpeedx * this.numada);
           this.elitePlusSpeedy = (int) Math.round(this.elitePlusSpeedy * this.numada);

           this.cycleDurationEnemyGenerate = (int) Math.round(this.cycleDurationEnemyGenerate * this.alpha); //敌机产生周期
           this.cycleDurationEnemyShoot = (int) Math.round(this.cycleDurationEnemyShoot * this.alpha); //敌机射击周期
           this.cycleDurationHeroShoot = (int) Math.round(this.cycleDurationHeroShoot * this.alpha); //英雄机射击周期



            // 更新配置
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
    }
    @Override
    protected  void generateEnemyAction() {
        resultEnemyGenerate = timeCountAndNewCycleJudge(EnemyGenerateTime, cycleDurationEnemyGenerate);
        EnemyGenerateTime = (int) resultEnemyGenerate[1];
        if (((boolean) resultEnemyGenerate[0])) {
            synchronized (enemyAircrafts) {
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    if (config.score >= threshold && config.score / threshold > lastBossScore / threshold && bossList.isEmpty()) {
                        AbstractEnemyAircraft boss = bossFactory.createEnemy();
                        enemyAircrafts.add(boss);
                        bossList.add(boss);
                        shakeThread = new Thread(new ShakeThread());
                        shakeThread.start();
                        if (this.usemusic) {
                            boss_gbmThread = new MusicThread("src/videos/bgm_boss.wav", 0);
                            all_bgmThread.pauseMusic();
                            boss_gbmThread.start();
                        }
                        lastBossScore = config.score;
                    } else {
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
    protected  void aircraftsMoveAction() {
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
                        if (enemyAircraft instanceof Boss && this.usemusic) {
                            boss_gbmThread.stopmusic();
                            all_bgmThread.resumeMusic();
                            this.enemyMaxNumber ++;
                            this.BossHp = (int) Math.round(this.BossHp * this.beta);
                            this.BossPower = (int) Math.round(this.BossPower * this.beta);
                            this.BossSpeedx = (int) Math.round(this.BossSpeedx * this.beta);
                            this.threshold = (int) Math.round(this.threshold * this.gamma);

                            config.setBossHp(BossHp);
                            config.setBossPower(BossPower);
                            config.setBossSpeedx(BossSpeedx);
                            System.out.println("恭喜您成功击败BOSS！ 游戏难度提升： 最大敌机数量+1 " + this.enemyMaxNumber+"下一次Boss属性提升为"+this.beta+"倍"+"BOSS出现阈值变为"+this.gamma+"倍");
                        }
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
        bossList.removeIf(AbstractFlyingObject::notValid);
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
        paintBossHp(g);
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

    private void paintBossHp(Graphics g) {
        if (!bossList.isEmpty()) {
            AbstractEnemyAircraft boss = bossList.get(0);
            int bossHp = boss.getHp();
            // 设置矩形框的位置和大小
            int barWidth = 200; // 血量条的固定宽度
            int barHeight = 20;
            int barX = 10; // 血量条左上角的 x 坐标
            int barY = 60; // 血量条左上角的 y 坐标

            // 计算血量条的颜色
            Color barColor = Color.RED;

            // 计算当前血量对应的矩形宽度
            int currentWidth = (int) ((double) bossHp / boss.getMaxHp() * barWidth);

            // 绘制血量条的边框
            g.setColor(Color.BLACK);
            g.drawRect(barX, barY, barWidth, barHeight);

            // 绘制血量条的填充部分
            g.setColor(barColor);
            g.fillRect(barX, barY, currentWidth, barHeight);
        }

    }
}
