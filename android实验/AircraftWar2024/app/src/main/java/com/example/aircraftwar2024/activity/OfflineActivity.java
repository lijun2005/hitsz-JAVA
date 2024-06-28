package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.aircraftwar2024.R;

public class OfflineActivity extends AppCompatActivity {
    public static final String TAG = "OfflineActivity";
    private boolean useMusic;
    private Button simpleButton;
    private  Button middleButton;
    private Button hardButton;
    private  int gameType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        setContentView(R.layout.activity_offline);
        useMusic = getIntent().getBooleanExtra("usemusic",false);
        simpleButton  = (Button) findViewById(R.id.simplebutton);
        hardButton = (Button) findViewById(R.id.hardbutton);
        middleButton = (Button) findViewById(R.id.middlebutton);

        simpleButton.setOnClickListener(view -> {
            Intent intent = new Intent(OfflineActivity.this,GameActivity.class);
            intent.putExtra("usemusic",useMusic);
            intent.putExtra("gameType",1);
            startActivity(intent);
                }
        );
        middleButton.setOnClickListener(view -> {
                    Intent intent = new Intent(OfflineActivity.this,GameActivity.class);
                    intent.putExtra("usemusic",useMusic);
                    intent.putExtra("gameType",2);
                    startActivity(intent);
                }
        );
        hardButton.setOnClickListener(view -> {
                    Intent intent = new Intent(OfflineActivity.this,GameActivity.class);
                    intent.putExtra("usemusic",useMusic);
                    intent.putExtra("gameType",3);
                    startActivity(intent);
                }
        );
    }
}