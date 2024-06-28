package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.MediaPlayer;

public class MyMediaPlayer {
    private MediaPlayer bgMp;
    private int position;
    public MyMediaPlayer(Context context, int resid){
        bgMp = MediaPlayer.create(context,resid);
    }

    public void start(){
        bgMp.start();
        bgMp.setLooping(true);
    }
    public void pause(){
        position = bgMp.getCurrentPosition();
        bgMp.pause();
    }
    public void resume(){
        bgMp.seekTo(position);
        bgMp.start();
    }
    public void stop(){
        bgMp.stop();
        bgMp.release();
        bgMp = null;
    }
}
