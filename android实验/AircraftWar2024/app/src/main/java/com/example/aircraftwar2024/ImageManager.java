package com.example.aircraftwar2024;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.aircraftwar2024.aircraft.Boss;
import com.example.aircraftwar2024.aircraft.EliteEnemy;
import com.example.aircraftwar2024.aircraft.ElitePlusEnemy;
import com.example.aircraftwar2024.aircraft.HeroAircraft;
import com.example.aircraftwar2024.aircraft.MobEnemy;
import com.example.aircraftwar2024.bullet.EnemyBullet;
import com.example.aircraftwar2024.bullet.HeroBullet;
import com.example.aircraftwar2024.prop.BombProp;
import com.example.aircraftwar2024.prop.BulletProp;
import com.example.aircraftwar2024.prop.BulletPlusProp;
import com.example.aircraftwar2024.prop.BloodProp;
import com.example.aircraftwar2024.prop.BloodPlusProp;
import com.example.aircraftwar2024.prop.ScoreProp;

import java.util.HashMap;
import java.util.Map;


public class ImageManager {
    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, Bitmap> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static Bitmap BACKGROUND1_IMAGE;
    public static Bitmap BACKGROUND2_IMAGE;
    public static Bitmap BACKGROUND3_IMAGE;
    public static Bitmap HERO_IMAGE;
    public static Bitmap HERO_BULLET_IMAGE;
    public static Bitmap ENEMY_BULLET_IMAGE;
    public static Bitmap MOB_ENEMY_IMAGE;
    public static Bitmap ELITE_ENEMY_IMAGE;
    public static Bitmap BOSS_ENEMY_IMAGE;
    public static Bitmap FIRE_SUPPLY_IMAGE;
    public static Bitmap HP_SUPPLY_IMAGE;
    public static Bitmap BOMB_SUPPLY_IMAGE;

    public static Bitmap ELITE_PLUS_IMAGE;
    public static  Bitmap BULLET_PLUS_IMAGE;
    public static Bitmap BLOOD_PLUS_IMAGE;
    public static  Bitmap SCORE_IMAGE;
    public static void initImage(Context context){

        ImageManager.BACKGROUND1_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        ImageManager.HERO_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero);
        ImageManager.BACKGROUND2_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg2);
        ImageManager.BACKGROUND3_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg4);
        ImageManager.MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.mob);
        ImageManager.ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.elite);
        ImageManager.BOSS_ENEMY_IMAGE = BitmapFactory.decodeResource(context.getResources(),R.drawable.boss);
        ImageManager.HERO_BULLET_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_hero);
        ImageManager.ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_enemy);
        ImageManager.FIRE_SUPPLY_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_bullet);
        ImageManager.HP_SUPPLY_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_blood);
        ImageManager.BOMB_SUPPLY_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_bomb);

        ImageManager.ELITE_PLUS_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.eliteplus);
        ImageManager.BULLET_PLUS_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_bulletplus);
        ImageManager.BLOOD_PLUS_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_bloodplus);
        ImageManager.SCORE_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_score);

        CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
        CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(Boss.class.getName(), BOSS_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BulletProp.class.getName(), FIRE_SUPPLY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BloodProp.class.getName(), HP_SUPPLY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BombProp.class.getName(), BOMB_SUPPLY_IMAGE);

        CLASSNAME_IMAGE_MAP.put(BulletPlusProp.class.getName(), BULLET_PLUS_IMAGE);
        CLASSNAME_IMAGE_MAP.put(ScoreProp.class.getName(), SCORE_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BloodPlusProp.class.getName(), BLOOD_PLUS_IMAGE);
        CLASSNAME_IMAGE_MAP.put(ElitePlusEnemy.class.getName(), ELITE_PLUS_IMAGE);
    }

    public static Bitmap get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static Bitmap get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }
}
