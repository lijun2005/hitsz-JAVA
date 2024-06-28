package com.example.aircraftwar2024.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;


public class MainActivity extends AppCompatActivity {
    private boolean useMusic = false;
    private  Button offlinebutton;
    private  RadioGroup musicradio;

    private  Button onlinebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        setContentView(R.layout.activity_main);
        offlinebutton=(Button) findViewById(R.id.offlinebutton);
        offlinebutton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
            intent.putExtra("usemusic",useMusic);
            startActivity(intent);
        });

        musicradio = (RadioGroup) findViewById(R.id.musicbtn);
        musicradio.setOnCheckedChangeListener((radioGroup, i) -> {
            if(i==R.id.muscion){
                useMusic=true;
                Log.i("MainActivity","打开音乐");
            } else if (i==R.id.muscioff) {
                useMusic=true;
                Log.i("MainActivity","关闭音乐");
            }
        });

        onlinebutton = (Button) findViewById(R.id.onlinebutton);
        onlinebutton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, OnlineActivity.class);
            intent.putExtra("usemusic",useMusic);
            startActivity(intent);
        });

    }
}