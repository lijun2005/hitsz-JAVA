package com.example.aircraftwar2024.game;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;

import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.activity.config;
import com.example.aircraftwar2024.aircraft.AbstractEnemyAircraft;
import com.example.aircraftwar2024.aircraft.Boss;
import com.example.aircraftwar2024.aircraft.BossFactory;
import com.example.aircraftwar2024.aircraft.EnemyFactory;
import com.example.aircraftwar2024.aircraft.HeroAircraft;
import com.example.aircraftwar2024.basic.AbstractFlyingObject;
import com.example.aircraftwar2024.bullet.BaseBullet;
import com.example.aircraftwar2024.music.MyMediaPlayer;
import com.example.aircraftwar2024.prop.BaseProp;
import com.example.aircraftwar2024.prop.BombProp;
import com.example.aircraftwar2024.threadProcess.ShakeThread;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class MiddleGame extends BaseGame {

    public static final String TAG = "MiddleGame";
    private final List<AbstractEnemyAircraft> bossList;



    public int threshold;
    public int lastBossScore;
    private double numada;
    private double alpha;

    private final EnemyFactory bossFactory = new BossFactory();
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */


    private int cycleDuration;


    private int methodTime = 0;

    private Object[] resultmethod;

    private final Random rand = new Random();
    private MyMediaPlayer boss_bgm;

    public MiddleGame(Context context, boolean useMusic, Handler handler) {
        super(context,useMusic,handler);
        this.backGround = ImageManager.BACKGROUND2_IMAGE;
        bossList = new LinkedList<>();
        initializeParameter();
    }


    @Override
    protected void initializeParameter() {
        this.numada = 1.01;
        this.alpha = 0.99;
        this.eliteProb = 0.4;
        this.elitePlusProb = 0.2;
        this.threshold = 1000;
        this.lastBossScore = 0;
        this.cycleDurationEnemyGenerate = 600; //敌机产生周期
        this.cycleDurationEnemyShoot = 600; //敌机射击周期
        this.cycleDurationHeroShoot = 500; //英雄机射击周期
        this.cycleDuration = 10000; //每隔10s增大难度为原来的namada倍

        this.method_kind = 1;
        this.enemyMaxNumber = 3;

        this.heroPower = 60;
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

        this.BossHp = 500;
        this.BossPower = 20;
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
           Log.i("MiddleGame","游戏难度提升 所有的属性提升" + this.numada + "倍,Boss 属性除外，周期变为原来的" + this.alpha + "倍");
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

           this.cycleDurationEnemyGenerate = (int) Math.round(this.cycleDurationEnemyGenerate * this.alpha); // 敌机产生周期
           this.cycleDurationEnemyShoot = (int) Math.round(this.cycleDurationEnemyShoot * this.alpha); // 敌机射击周期
           this.cycleDurationHeroShoot = (int) Math.round(this.cycleDurationHeroShoot * this.alpha); // 英雄机射击周期



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
                    if (config.score >= lastBossScore+threshold  && bossList.isEmpty()) {
                        Log.i(TAG, "Score: " + config.score + ", Last Boss Score: " + lastBossScore + ", Threshold: " + threshold);
                        if(this.usemusic){
                            this.boss_bgm = new MyMediaPlayer(this.getContext(), R.raw.bgm_boss);
                            this.boss_bgm.start();
                            this.all_bgm.pause();
                        }
                        AbstractEnemyAircraft boss = bossFactory.createEnemy();
                        enemyAircrafts.add(boss);
                        bossList.add(boss);
                        shakeThread = new Thread(new ShakeThread());
                        shakeThread.start();
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
                        if(this.usemusic){
                            this.bullmusic.start();
                        }
                        if (enemyAircraft instanceof Boss) {
                            this.enemyMaxNumber ++;
                            Log.i(TAG,"恭喜您成功击败BOSS！ 游戏难度提升： 最大敌机数量+1 " + this.enemyMaxNumber);
                            if(this.usemusic){
                                this.boss_bgm.pause();
                                this.all_bgm.resume();
                            }
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
                if (prop instanceof
                        BombProp) {
                    if(usemusic){
                        bombmusic.start();
                    }
                    ((BombProp) prop).addAircrafts(enemyAircrafts);
                    ((BombProp) prop).addBullets(enemyBullets);
                }
                else{
                    if(usemusic){
                        supplymusic.start();
                    }
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

    public void draw() {
        canvas = mSurfaceHolder.lockCanvas();
        if(mSurfaceHolder == null || canvas == null){
            return;
        }

        //绘制背景，图片滚动
        canvas.drawBitmap(backGround,0,this.backGroundTop-backGround.getHeight(),mPaint);
        canvas.drawBitmap(backGround,0,this.backGroundTop,mPaint);
        backGroundTop +=1;
        if (backGroundTop == GameActivity.screenHeight)
            this.backGroundTop = 0;

        //先绘制子弹，后绘制飞机
        paintImageWithPositionRevised(enemyBullets); //敌机子弹


        paintImageWithPositionRevised(heroBullets);  //英雄机子弹


        paintImageWithPositionRevised(enemyAircrafts);//敌机

        paintImageWithPositionRevised(props);//道具

        canvas.drawBitmap(ImageManager.HERO_IMAGE,
                heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY()- ImageManager.HERO_IMAGE.getHeight() / 2,
                mPaint);

        paintScoreAndLife(canvas,mPaint);
        paintBossHp(canvas,mPaint);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }



    private void paintBossHp(Canvas canvas,Paint paint) {
        if (!bossList.isEmpty()) {
            AbstractEnemyAircraft boss = bossList.get(0);
            int bossHp = boss.getHp();

            int barWidth = 400;
            int barHeight = 100;
            int barX = 10;
            int barY = 300;

            int currentWidth = (int) ((double) bossHp / boss.getMaxHp() * barWidth);

            // Draw the border of the health bar
            paint.setColor(Color.BLACK);
            canvas.drawRect(barX, barY, barX + barWidth, barY + barHeight, paint);

            // Draw the filled part of the health bar
            paint.setColor(Color.RED);
            canvas.drawRect(barX, barY, barX + currentWidth, barY + barHeight, paint);
        }
}

    @Override
    protected void stopMusic(){
        if(this.all_bgm!=null){
            this.all_bgm.stop();
        }
        if(this.boss_bgm!=null){
            this.boss_bgm.stop();
        }
    }
}
