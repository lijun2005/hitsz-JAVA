package com.example.aircraftwar2024.threadProcess;


import com.example.aircraftwar2024.activity.config;

public class ShakeThread implements  Runnable{
    public long duration=1000;

    @Override
    public void run() {
        config.setIsShake(true);
        try {
            Thread.sleep(this.duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        config.setIsShake(false);
    }


}