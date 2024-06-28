package com.example.aircraftwar2024.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.activity.config;
import com.example.aircraftwar2024.aircraft.AbstractAircraft;
import com.example.aircraftwar2024.aircraft.AbstractEnemyAircraft;
import com.example.aircraftwar2024.aircraft.HeroAircraft;
import com.example.aircraftwar2024.basic.AbstractFlyingObject;
import com.example.aircraftwar2024.bullet.BaseBullet;
import com.example.aircraftwar2024.music.MySoundPool;
import com.example.aircraftwar2024.prop.BaseProp;
import com.example.aircraftwar2024.prop.BombProp;
import com.example.aircraftwar2024.scorePattern.User;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;


public class EasyGame extends BaseGame{
    private final Random rand = new Random();
    public EasyGame(Context context, boolean useMusic, Handler handler) {
        super(context,useMusic,handler);
        this.backGround = ImageManager.BACKGROUND1_IMAGE;
        this.enemyMaxNumber = 2;
        initializeParameter();
    }


    @Override
    protected void initializeParameter() {
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
    }



    @Override
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

        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }


}
