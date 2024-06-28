package com.example.aircraftwar2024.music;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import com.example.aircraftwar2024.R;

import java.util.HashMap;

public class MySoundPool {
    private SoundPool mysp;
    private HashMap<Integer, Integer> soundPoolMap;
    private boolean isLoaded = false;

    public MySoundPool(Context context, int musicid) {
        createSoundPool(context, musicid);
        soundPoolMap = new HashMap<>();
        soundPoolMap.put(1, mysp.load(context, musicid, 1));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createSoundPool(Context context, int musicid) {
        if (mysp == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                mysp = new SoundPool.Builder()
                        .setMaxStreams(10) // 1000 seems too high; typically, a smaller number is more reasonable.
                        .setAudioAttributes(audioAttributes)
                        .build();
            } else {
                mysp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            }
        }
        // Set the onLoadCompleteListener to know when the sound is loaded
        mysp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) {
                    isLoaded = true;
                    Log.d("MySoundPool", "Sound loaded with ID: " + sampleId);
                    // Optionally, you can start playing the sound here if needed.
                    // start(); // Uncomment if you want to auto-play once loaded
                } else {
                    Log.e("MySoundPool", "Failed to load sound with ID: " + sampleId);
                }
            }
        });
    }

    public void start() {
        if (!isLoaded) {
            Log.w("MySoundPool", "Sound not loaded yet, cannot play.");
            return;
        }

        Integer soundID = soundPoolMap.get(1);
        if (soundID != null) {
            Log.d("MySoundPool", "开始播放 " + soundID);
            float leftVolume = 1.0f;
            float rightVolume = 1.0f;
            int priority = 1;
            int loop = 0;
            float rate = 1.0f;
            mysp.play(soundID, leftVolume, rightVolume, priority, loop, rate);
            Log.d("MySoundPool", "Sound playing with ID: " + soundID);
        } else {
            Log.e("MySoundPool", "Sound ID is null");
        }
    }
}
