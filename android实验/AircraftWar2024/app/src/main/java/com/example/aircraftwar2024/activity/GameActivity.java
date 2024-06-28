package com.example.aircraftwar2024.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MiddleGame;
import com.example.aircraftwar2024.scorePattern.User;
import com.example.aircraftwar2024.scorePattern.UserDao;
import com.example.aircraftwar2024.scorePattern.UserDaoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";
    public Handler mHandler;
    private int gameType=0;
    private boolean useMusic = false;
    public static int screenWidth,screenHeight;
    private String file;
    private BaseGame baseGameView;

    private UserDaoImpl userdao;

    private TextView textRecord;
    private  SimpleAdapter listItemAdapter;
    private  ListView list;
    private Button returnButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        getScreenHW();
        mHandler = new Mhandler();
        InitializeGame();
        userdao = new UserDaoImpl(file,this);
    }

    public void getScreenHW(){
        DisplayMetrics dm = new DisplayMetrics();
        getDisplay().getRealMetrics(dm);
        screenWidth= dm.widthPixels;
        screenHeight = dm.heightPixels;
        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void InitializeGame(){
        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
            useMusic = getIntent().getBooleanExtra("usemusic",false);
        }
        if (gameType==1){
            this.file="easy.dat";
            baseGameView = new EasyGame(this,useMusic,mHandler);
            setContentView(baseGameView);}
        else if(gameType==2)
        {   this.file = "middle.dat";
            baseGameView = new MiddleGame(this,useMusic,mHandler);
            setContentView(baseGameView);}
        else if(gameType==3)
        {
            this.file = "hard.dat";
            baseGameView = new HardGame(this,useMusic,mHandler);
            setContentView(baseGameView);}
    }

    class Mhandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            Bundle bundle = msg.getData();
            String userName = bundle.getString("userName");
            int userScore = bundle.getInt("userScore");
            String userTime = bundle.getString("userTime");
            User user = new User(userName,userScore,userTime);
            userdao.AddUser(user);
            userdao.save();
            InitializeUI();
        }
    }

    public void InitializeUI(){
        setContentView(R.layout.activity_record);
        textRecord = findViewById(R.id.textRecord);
        list = findViewById(R.id.ListView01);
        returnButton = findViewById(R.id.returnButton);
        if(gameType==1)
            textRecord.setText("简单模式排行榜");
        else if(gameType==2)
            textRecord.setText("中等模式排行榜");
        else if (gameType==3)
            textRecord.setText("困难模式排行榜");
        List<Map<String, Object>> data = getData();
        listItemAdapter = new SimpleAdapter(
                this,
                data,
                R.layout.activity_item,
                new String[]{"排名", "用户名", "得分", "时间"},
                new int[]{R.id.rank, R.id.username, R.id.score, R.id.time}
        );
        View header = getLayoutInflater().inflate(R.layout.list_header, list, false);
        list.addHeaderView(header);
        list.setAdapter(listItemAdapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Map<String, Object> clkmap = (Map<String, Object>) arg0.getItemAtPosition(arg2);
                int line  = (int) arg3+1;
                Dialog aletDialog = new AlertDialog.Builder(GameActivity.this)
                        .setTitle("提示")
                        .setMessage("确定删除第"+line+"行数据吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userdao.DeleteUser(clkmap.get("时间").toString());
                                userdao.save();
                                updateUI();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                aletDialog.show();
            }
        });
        returnButton.setOnClickListener(view -> {
            returnToFirstActivity();
        });
    }

    public void updateUI(){
        List<Map<String, Object>> data = getData();
        listItemAdapter = new SimpleAdapter(
                this,
                data,
                R.layout.activity_item,
                new String[]{"排名", "用户名", "得分", "时间"},
                new int[]{R.id.rank, R.id.username, R.id.score, R.id.time}
        );
        list.setAdapter(listItemAdapter);
    }

    private List<Map<String,Object>> getData(){
        ArrayList<User> users = userdao.getAllUser();
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (int i=0;i<users.size();i++){
            map = new HashMap<>();
            map.put("排名",String.valueOf(i+1));
            map.put("用户名",users.get(i).getUserName());
            map.put("得分",users.get(i).getUserScore());
            map.put("时间",users.get(i).getUserTime());
            listitem.add(map);
        }
        return listitem;
    }

    private void returnToFirstActivity() {
        ActivityManager activityManager = ActivityManager.getActivityManager();
        activityManager.finishAllActivity();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}