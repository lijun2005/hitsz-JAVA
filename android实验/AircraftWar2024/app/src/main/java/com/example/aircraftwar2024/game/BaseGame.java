package com.example.aircraftwar2024.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.NonNull;
import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.activity.config;
import com.example.aircraftwar2024.aircraft.AbstractAircraft;
import com.example.aircraftwar2024.aircraft.AbstractEnemyAircraft;
import com.example.aircraftwar2024.aircraft.EliteFactory;
import com.example.aircraftwar2024.aircraft.ElitePlusFactory;
import com.example.aircraftwar2024.aircraft.EnemyFactory;
import com.example.aircraftwar2024.aircraft.HeroAircraft;
import com.example.aircraftwar2024.aircraft.MobFactory;
import com.example.aircraftwar2024.basic.AbstractFlyingObject;
import com.example.aircraftwar2024.bullet.BaseBullet;
import com.example.aircraftwar2024.music.MyMediaPlayer;
import com.example.aircraftwar2024.music.MySoundPool;
import com.example.aircraftwar2024.prop.BaseProp;
import com.example.aircraftwar2024.threadProcess.ShakeThread;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 游戏逻辑抽象基类，遵循模板模式，action() 为模板方法
 * 包括：游戏主面板绘制逻辑，游戏执行逻辑。
 * 子类需实现抽象方法，实现相应逻辑
 * @author hitsz
 */
public abstract class BaseGame extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    Context context;
    protected  MySoundPool bullmusic;
    protected  MySoundPool overmusic;
    protected  MySoundPool supplymusic;
    protected  MySoundPool bombmusic;



    protected  MyMediaPlayer all_bgm;
    public static final String TAG = "BaseGame";
    boolean mbLoop; //控制绘画线程的标志位
    protected final SurfaceHolder mSurfaceHolder;
    protected Canvas canvas;  //绘图的画布
    protected final Paint mPaint;

    //点击屏幕位置
    float clickX = 0, clickY=0;

    protected int backGroundTop = 0;

    /**
     * 背景图片缓存，可随难度改变
     */
    protected Bitmap backGround;
    protected boolean mIsDrawing;

    protected Handler mainHandler;


    /**
     * 普通和困难模式中
     * 当得分每超过一次bossScoreThreshold，则产生一次boss机
     */
    protected int bossScoreThreshold = Integer.MAX_VALUE;



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

    protected  String mapPath;

    protected  boolean gameOverFlag ;

    protected     double eliteProb = 0;
    protected     double elitePlusProb = 0;

    protected double probability = 0.;



    protected Object[] resultEnemyGenerate;
    protected Object[] resultEnemyShoot;
    protected Object[] resultHeroShoot;
    protected HeroAircraft heroAircraft;

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

//    protected MyMediaPlayer all_bgm;
    public BaseGame(Context context,boolean useMusic,Handler handler){
        super(context);
        this.mainHandler = handler;
        mbLoop = true;
        mPaint = new Paint();  //设置画笔
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        this.setFocusable(true);
        ImageManager.initImage(context);
        this.context = context;

        enemyAircrafts = new CopyOnWriteArrayList<>();
        heroBullets =   new CopyOnWriteArrayList<>();
        enemyBullets =  new CopyOnWriteArrayList<>();
        props = new CopyOnWriteArrayList<>();

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


        this.usemusic = useMusic;
        if(this.usemusic){
            Log.i(TAG,"开始播放音乐");
            overmusic = new MySoundPool(context,R.raw.game_over);
            bombmusic = new MySoundPool(context,R.raw.bomb_explosion);
            supplymusic = new MySoundPool(context,R.raw.get_supply);
            bullmusic = new MySoundPool(context,R.raw.bullet_hit);
            all_bgm = new MyMediaPlayer(this.getContext(), R.raw.bgm);
            all_bgm.start();
        }
        gameOverFlag = false;

        time = 0;
        timeInterval = 16;
        backGroundTop = 0;
        mIsDrawing=true;
        heroAircraft = HeroAircraft.getInstance();
        heroController();
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    final public void action() {
        Runnable task = () -> {
            time += timeInterval;


            generateEnemyAction();
//            //敌机射击
            enemyShootAction();

            //英雄机射击
            heroShootAction();
//
//            // 子弹移动
            bulletsMoveAction();
//
            // 飞机移动
            aircraftsMoveAction();
//
            // 道具移动
            propsMoveAction();
//            // 撞击检测
            crashCheckAction();
//
//            // 后处理
            postProcessAction();
            changeParameter();
            gameoverProcessAction();
        };
        task.run();
    }
    public void heroController(){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clickX = motionEvent.getX();
                clickY = motionEvent.getY();
                heroAircraft.setLocation(clickX, clickY);

                if ( clickX<0 || clickX> GameActivity.screenWidth || clickY<0 || clickY>GameActivity.screenHeight){
                    // 防止超出边界
                    return false;
                }
                return true;
            }
        });
    }
    protected  abstract void initializeParameter();
    protected  abstract void changeParameter();
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


    protected  void enemyShootAction() {
        resultEnemyShoot = timeCountAndNewCycleJudge(EnemyShootTime, cycleDurationEnemyShoot);
        EnemyShootTime = (int) resultEnemyShoot[1];
        if (((boolean) resultEnemyShoot[0])) {
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
        }
    }

    protected   void heroShootAction() {
        resultHeroShoot = timeCountAndNewCycleJudge(HeroShootTime, cycleDurationHeroShoot);
        HeroShootTime = (int) resultHeroShoot[1];
        if ((boolean) resultHeroShoot[0]) {
            heroBullets.addAll(heroAircraft.shoot());
        }
    }



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
    protected void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    protected  abstract void crashCheckAction();

    protected   abstract  void postProcessAction();



    abstract  public void draw();


    protected void paintImageWithPositionRevised(List<? extends AbstractFlyingObject> objects) {
        int SHAKE_AMPLITUDE = 10; // 震动幅度，像素
        Random random = new Random();
        int offsetX = 0;
        int offsetY = 0;
        if(config.isShake){
            offsetX =random.nextInt(SHAKE_AMPLITUDE * 2) - SHAKE_AMPLITUDE;
            offsetY =random.nextInt(SHAKE_AMPLITUDE * 2) - SHAKE_AMPLITUDE   ;

        }
        if (objects.size() == 0) {
            return;
        }
        for (AbstractFlyingObject object : objects) {
            if (object.notValid())
                    continue;
            Bitmap image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            canvas.drawBitmap(image, object.getLocationX() - image.getWidth() / 2+offsetX,
                    object.getLocationY() - image.getHeight() / 2+offsetY, mPaint);
        }
    }

    public void paintScoreAndLife(Canvas canvas, Paint paint){
        int x = 10;
        int y = 100;

        // Set the color to red
        paint.setColor(Color.RED);

        // Set the font size and style
        paint.setTextSize(80);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));

        // Draw the score on the canvas
        canvas.drawText("SCORE: " + config.score, x, y, paint);

        // Adjust the y position for the next line of text
        y = y + 100;

        // Draw the life on the canvas
        canvas.drawText("LIFE: " + this.heroAircraft.getHp(), x, y, paint);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        GameActivity.screenWidth = i1;
        GameActivity.screenHeight = i2;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
       mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            action();
            draw();
        }
    }


     protected   void gameoverProcessAction() {
        if (heroAircraft.notValid()) {
            stopMusic();
            overMusic();
            gameOverFlag = true;
            mIsDrawing=false;
            int userScore = config.score;
            HeroAircraft.resetInstance();
            config.resetToDefault();
            LocalDateTime dateTime = LocalDateTime.now(); // get the current date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String userName = "test";
            String userTime = dateTime.format(formatter);
            Message msg = Message.obtain();
            msg.what = 0;
            Bundle bundle = new Bundle();
            bundle.putString("userName", userName);
            bundle.putInt("userScore", userScore);
            bundle.putString("userTime", userTime);
            msg.setData(bundle);
            this.mainHandler.sendMessage(msg);
        }
    }

    protected  void stopMusic(){
        if(this.all_bgm!=null){
            this.all_bgm.stop();
        }
    }
    protected void overMusic(){
        if(this.usemusic){
            overmusic.start();
        }
    }
}
