package com.example.aircraftwar2024.activity;

public class config {
    // Default values
    private static final int DEFAULT_HERO_POWER = 30;
    private static final int DEFAULT_HERO_HP = 1000;
    private static final int DEFAULT_MOB_HP = 30;
    private static final int DEFAULT_MOB_SPEEDX = 0;
    private static final int DEFAULT_MOB_SPEEDY = 10;
    private static final int DEFAULT_ELITE_HP = 30;
    private static final int DEFAULT_ELITE_POWER = 0;
    private static final int DEFAULT_ELITE_SPEEDX = 10;
    private static final int DEFAULT_ELITE_SPEEDY = 10;
    private static final int DEFAULT_ELITE_PLUS_HP = 30;
    private static final int DEFAULT_ELITE_PLUS_POWER = 20;
    private static final int DEFAULT_ELITE_PLUS_SPEEDX = 4;
    private static final int DEFAULT_ELITE_PLUS_SPEEDY = 10;
    private static final int DEFAULT_BOSS_HP = 500;
    private static final int DEFAULT_BOSS_POWER = 50;
    private static final int DEFAULT_BOSS_SPEEDX = 1;
    private static final int DEFAULT_BOSS_SPEEDY = 0;
    private static final int DEFAULT_CYCLE_DURATION_HERO_SHOOT = 600;
    private static final int DEFAULT_METHOD_KIND = 0;
    public static final int DEFAULT_SCORE = 0;
    public static final boolean SHAKE= false;

    public  static  boolean isShake = SHAKE;

    public static boolean isIsShake() {
        return isShake;
    }

    public static void setIsShake(boolean isShake) {
        config.isShake = isShake;
    }

    // Static fields
    private static int method_kind = DEFAULT_METHOD_KIND;
    private static int cycleDurationHeroShoot = DEFAULT_CYCLE_DURATION_HERO_SHOOT;
    private static int heroPower = DEFAULT_HERO_POWER;
    private static int heroHp = DEFAULT_HERO_HP;
    public static int score = DEFAULT_SCORE;
    private static int mobHp = DEFAULT_MOB_HP;
    private static int mobSpeedx = DEFAULT_MOB_SPEEDX;
    private static int mobSpeedy = DEFAULT_MOB_SPEEDY;
    private static int eliteHp = DEFAULT_ELITE_HP;
    private static int elitePower = DEFAULT_ELITE_POWER;
    private static int eliteSpeedx = DEFAULT_ELITE_SPEEDX;
    private static int eliteSpeedy = DEFAULT_ELITE_SPEEDY;
    private static int elitePlusHp = DEFAULT_ELITE_PLUS_HP;
    private static int elitePlusPower = DEFAULT_ELITE_PLUS_POWER;
    private static int elitePlusSpeedx = DEFAULT_ELITE_PLUS_SPEEDX;
    private static int elitePlusSpeedy = DEFAULT_ELITE_PLUS_SPEEDY;
    private static int BossHp = DEFAULT_BOSS_HP;
    private static int BossPower = DEFAULT_BOSS_POWER;
    private static int BossSpeedx = DEFAULT_BOSS_SPEEDX;
    private static int BossSpeedy = DEFAULT_BOSS_SPEEDY;

    // Getter and setter methods
    // ...

    // Method to reset configuration to default values
    public static void resetToDefault() {
        method_kind = DEFAULT_METHOD_KIND;
        cycleDurationHeroShoot = DEFAULT_CYCLE_DURATION_HERO_SHOOT;
        heroPower = DEFAULT_HERO_POWER;
        heroHp = DEFAULT_HERO_HP;
        score = DEFAULT_SCORE;
        mobHp = DEFAULT_MOB_HP;
        mobSpeedx = DEFAULT_MOB_SPEEDX;
        mobSpeedy = DEFAULT_MOB_SPEEDY;
        eliteHp = DEFAULT_ELITE_HP;
        elitePower = DEFAULT_ELITE_POWER;
        eliteSpeedx = DEFAULT_ELITE_SPEEDX;
        eliteSpeedy = DEFAULT_ELITE_SPEEDY;
        elitePlusHp = DEFAULT_ELITE_PLUS_HP;
        elitePlusPower = DEFAULT_ELITE_PLUS_POWER;
        elitePlusSpeedx = DEFAULT_ELITE_PLUS_SPEEDX;
        elitePlusSpeedy = DEFAULT_ELITE_PLUS_SPEEDY;
        BossHp = DEFAULT_BOSS_HP;
        BossPower = DEFAULT_BOSS_POWER;
        BossSpeedx = DEFAULT_BOSS_SPEEDX;
        BossSpeedy = DEFAULT_BOSS_SPEEDY;
        isShake=SHAKE;
    }

    public static int getCycleDurationHeroShoot() {
        return cycleDurationHeroShoot;
    }

    public static void setCycleDurationHeroShoot(int cycleDurationHeroShoot) {
        config.cycleDurationHeroShoot = cycleDurationHeroShoot;
    }
    public static int getBossSpeedy() {
        return BossSpeedy;
    }

    public static void setBossSpeedy(int bossSpeedy) {
        BossSpeedy = bossSpeedy;
    }

    public static int getHeroPower() {
        return heroPower;
    }

    public static void setHeroPower(int heroPower) {
        config.heroPower = heroPower;
    }

    public static int getMethod_kind() {
        return method_kind;
    }

    public static void setMethod_kind(int method_kind) {
        config.method_kind = method_kind;
    }

    public static int getHeroHp() {
        return heroHp;
    }

    public static void setHeroHp(int heroHp) {
        config.heroHp = heroHp;
    }

    public static int getMobHp() {
        return mobHp;
    }

    public static void setMobHp(int mobHp) {
        config.mobHp = mobHp;
    }

    public static int getMobSpeedx() {
        return mobSpeedx;
    }

    public static void setMobSpeedx(int mobSpeedx) {
        config.mobSpeedx = mobSpeedx;
    }

    public static int getMobSpeedy() {
        return mobSpeedy;
    }

    public static void setMobSpeedy(int mobSpeedy) {
        config.mobSpeedy = mobSpeedy;
    }

    public static int getEliteHp() {
        return eliteHp;
    }

    public static void setEliteHp(int eliteHp) {
        config.eliteHp = eliteHp;
    }

    public static int getEliteSpeedx() {
        return eliteSpeedx;
    }

    public static void setEliteSpeedx(int eliteSpeedx) {
        config.eliteSpeedx = eliteSpeedx;
    }

    public static int getElitePower() {
        return elitePower;
    }

    public static void setElitePower(int elitePower) {
        config.elitePower = elitePower;
    }

    public static int getEliteSpeedy() {
        return eliteSpeedy;
    }

    public static void setEliteSpeedy(int eliteSpeedy) {
        config.eliteSpeedy = eliteSpeedy;
    }

    public static int getElitePlusHp() {
        return elitePlusHp;
    }

    public static void setElitePlusHp(int elitePlusHp) {
        config.elitePlusHp = elitePlusHp;
    }

    public static int getElitePlusPower() {
        return elitePlusPower;
    }

    public static void setElitePlusPower(int elitePlusPower) {
        config.elitePlusPower = elitePlusPower;
    }

    public static int getElitePlusSpeedx() {
        return elitePlusSpeedx;
    }

    public static void setElitePlusSpeedx(int elitePlusSpeedx) {
        config.elitePlusSpeedx = elitePlusSpeedx;
    }

    public static int getElitePlusSpeedy() {
        return elitePlusSpeedy;
    }

    public static void setElitePlusSpeedy(int elitePlusSpeedy) {
        config.elitePlusSpeedy = elitePlusSpeedy;
    }

    public static int getBossHp() {
        return BossHp;
    }

    public static void setBossHp(int bossHp) {
        BossHp = bossHp;
    }

    public static int getBossPower() {
        return BossPower;
    }

    public static void setBossPower(int bossPower) {
        BossPower = bossPower;
    }

    public static int getBossSpeedx() {
        return BossSpeedx;
    }

    public static void setBossSpeedx(int bossSpeedx) {
        BossSpeedx = bossSpeedx;
    }


}