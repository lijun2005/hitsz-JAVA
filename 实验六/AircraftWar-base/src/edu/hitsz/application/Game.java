package edu.hitsz.application;
import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.EliteFactory;
import edu.hitsz.aircraft.ElitePlusFactory;
import edu.hitsz.aircraft.EnemyFactory;
import edu.hitsz.aircraft.MobFactory;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.scorepattern.UserDao;
import edu.hitsz.threadprocess.MusicThread;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;

import edu.hitsz.threadprocess.ShakeThread;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

public abstract class Game extends JPanel {
    protected  int backGroundTop ;
    protected   ScheduledExecutorService executorService;
    protected   int timeInterval;
    public int time;

    protected   List<AbstractEnemyAircraft> enemyAircrafts;
    protected  List<BaseBullet> heroBullets;
    protected  List<BaseBullet> enemyBullets;
    protected  List<BaseProp> props;

    protected EnemyFactory mobFactory;
    protected EnemyFactory eliteFactory;
    protected EnemyFactory eliteplusFactory;


    protected  int cycleDurationEnemyGenerate; //敌机产生周期
    protected  int cycleDurationEnemyShoot; //敌机射击周期
    protected   int cycleDurationHeroShoot; //英雄机射击周期

    protected int EnemyGenerateTime ;
    protected int EnemyShootTime ;
    protected int HeroShootTime ;

    // // protected Object[] resultEnemyGenerate;
    // // protected Object[] resultEnemyShoot;
    // // protected Object[] resultHeroShoot;

    protected  String mapPath;

    protected  boolean gameOverFlag ;
    protected  boolean isPaused; // 游戏暂停标志
    protected   Object pauseLock = new Object(); // 用于暂停控制的锁对象
    protected     double eliteProb = 0;
    protected     double elitePlusProb = 0;

    protected  MusicThread all_bgmThread;
    protected  MusicThread gameover_bgmThread;
    protected  MusicThread boss_gbmThread;
    protected Thread shakeThread;

    protected  boolean usemusic;
    protected    int method_kind; //模式种类

    //英雄机相关设置
    protected    int heroPower; //子弹威力
    protected      int heroHp; //生命

    //普通敌机设置
    protected    int mobHp;//生命
    protected  int mobSpeedx;
    protected     int mobSpeedy;

    //精英敌机设置
    protected     int eliteHp;
    protected   int elitePower;
    protected     int eliteSpeedx;
    protected     int eliteSpeedy;


    //精英敌机plus设置
    protected     int elitePlusHp;
    protected   int elitePlusPower;
    protected     int elitePlusSpeedx;
    protected     int elitePlusSpeedy;
    //Boss设置

    protected     int BossHp;
    protected   int BossPower;
    protected     int BossSpeedx;
    protected int BossSpeedy;
    protected int enemyMaxNumber;
    
    // public int score;
    protected UserDao userdao;

    public Game(boolean usemusic,String mapPath) {
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        mobFactory = new MobFactory();
        eliteFactory = new EliteFactory();
        eliteplusFactory = new ElitePlusFactory();
        cycleDurationEnemyGenerate=600; //敌机产生周期
        cycleDurationEnemyShoot=600; //敌机射击周期
        cycleDurationHeroShoot=600; //英雄机射击周期
    
        EnemyGenerateTime=0 ;
        EnemyShootTime=0;
       HeroShootTime=0;
        shakeThread = new Thread(new ShakeThread());


        this.usemusic = usemusic;
        this.mapPath = mapPath;
        gameOverFlag = false;
        isPaused = false;
        time = 0;
        timeInterval = 40;
        backGroundTop = 0;
        // score = 0;
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        if (this.usemusic) {
            all_bgmThread = new MusicThread("src/videos/bgm.wav", 0);
            gameover_bgmThread = new MusicThread("src/videos/game_over.wav", 1);
            all_bgmThread.start();
        }
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    togglePause(); 
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();
    }


    final public void action() {
        initializeParameter();
        Runnable task = () -> {
            synchronized (pauseLock) {
                while (isPaused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            time += timeInterval;
            
            //敌机产生
            generateEnemyAction();
            //敌机射击
            enemyShootAction();

            //英雄机射击
            heroShootAction();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();
            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            // 每个时刻重绘界面
            repaint();

            changeParameter();
            gameoverProcessAction();
        };
        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //设定参数
    protected  abstract void initializeParameter();
    protected  abstract void changeParameter();

    protected void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            if (usemusic && all_bgmThread != null) {
                all_bgmThread.pauseMusic();
            }
            if (usemusic && boss_gbmThread != null) {
                boss_gbmThread.pauseMusic();
            }
        } else {
            synchronized (pauseLock) {
                if (usemusic && all_bgmThread != null) {
                    all_bgmThread.resumeMusic();
                }
                if (usemusic && boss_gbmThread != null) {
                    boss_gbmThread.resumeMusic();
                }
                pauseLock.notifyAll(); // 恢复游戏
            }
        }
    }


    protected  Object []  timeCountAndNewCycleJudge(int Time,int Duration) {
        Time += timeInterval;
        boolean isNewCycle = false;
        if (Time >= Duration && Time - timeInterval < Time) {
            // 跨越到新的周期
            Time %= Duration;
            isNewCycle = true;
        }
        
        return new Object[] { isNewCycle, Time };
    }

    protected   abstract void generateEnemyAction();

    protected   abstract  void enemyShootAction();
        

    protected  abstract  void heroShootAction();


    protected final void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }
       

    protected final void propsMoveAction() {
                for (BaseProp prop : props) {
            prop.forward();
        }
    }
       

    protected abstract void aircraftsMoveAction();

    protected  abstract void crashCheckAction();

    protected   abstract  void postProcessAction();

    protected  abstract void gameoverProcessAction();

    protected final void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        int SHAKE_AMPLITUDE = 10; // 震动幅度，像素
        Random random = new Random();
        int offsetX = 0;
        int offsetY = 0;
        if(config.isShake){
            offsetX =random.nextInt(SHAKE_AMPLITUDE * 2) - SHAKE_AMPLITUDE;
            offsetY =random.nextInt(SHAKE_AMPLITUDE * 2) - SHAKE_AMPLITUDE   ;

        }
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2+offsetX,
                    object.getLocationY() - image.getHeight() / 2+offsetY, null);
        }
    }


}
