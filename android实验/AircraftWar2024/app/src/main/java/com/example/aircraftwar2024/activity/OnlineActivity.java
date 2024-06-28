package com.example.aircraftwar2024.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.OnlineGame;
import com.example.aircraftwar2024.scorePattern.User;
import com.example.aircraftwar2024.scorePattern.UserDaoImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "OnlineActivity";
    public Handler mHandler;
    private boolean useMusic = false;
    private String file;
    private OnlineGame baseGameView;
    private  Button startButton;
    private Socket socket;
    private PrintWriter writer;


    private  int selfScore;
    private  int otherScore;
    private  boolean selfIsRunning;
    private  boolean otherIsRunning;
    private  boolean connectionStatus;

    private  Dialog alertDialog;
    private NetConn netConn;

    private TextView textRecord;
    private  SimpleAdapter listItemAdapter;
    private  ListView list;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        setContentView(R.layout.activity_online);
        startButton = findViewById(R.id.startbutton);
        startButton.setOnClickListener(this);
        selfScore = 0;
        otherScore = 100;
        mHandler  = new Mhandler();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.startbutton){
             netConn = new NetConn(mHandler);
             netConn.start();;
             alertDialog = new AlertDialog.Builder(OnlineActivity.this)
                    .setTitle("提示")
                    .setMessage("正在匹配对手.....")
                    .create();
            alertDialog.show();
        }

    }

    private void initializeGame(){
        selfIsRunning = true;
        if(getIntent() != null){
            useMusic = getIntent().getBooleanExtra("usemusic",false);
        }
        this.file="online.dat";
        baseGameView = new OnlineGame(this,useMusic,mHandler);
        setContentView(baseGameView);
        startSendingTasks();
    }
    private void startSendingTasks() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                baseGameView.postTask(otherScore);
                mHandler.postDelayed(this, 500);                // 持续向游戏进程发送对手分数
            }
        }, 500);
    }




    class Mhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: //代表本地游戏传输信息
                    selfScore = msg.getData().getInt("selfScore");
                    selfIsRunning = msg.getData().getBoolean("isRunning");
                    Log.i(TAG, "游戏发送分数selfScore:"+String.valueOf(selfScore));
                    break;
                case 2: //代表服务器端传输信息
                    otherScore = msg.getData().getInt("otherScore");
                    otherIsRunning = msg.getData().getBoolean("otherIsRunning");
                    break;
                case 3:
                    connectionStatus = msg.getData().getBoolean("connectionStatus");
                    alertDialog.dismiss();
                    initializeGame();
                    break;
                case 4://代表已经停止了
                    selfScore = msg.getData().getInt("selfScore");
                    selfIsRunning = msg.getData().getBoolean("isRunning");
                    Log.i(TAG, "游戏发送分数selfScore:"+String.valueOf(selfScore));
                    InitializeUI();
                    updateUI();
                    Log.i(TAG,"自己游戏已经结束等待敌机中");
                    // 使用Handler实现定时更新
                    final Handler handler = new Handler();
                    final Runnable updateTask = new Runnable() {
                        @Override
                        public void run() {
                            if (otherIsRunning) {
                                updateUI();
                                handler.postDelayed(this, 100); // 100ms后再次执行
                            } else {
                                updateUI(); // 最终的排行榜更新
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Log.i(TAG, "敌机游戏也结束，显示最终排行榜");
                            }
                        }
                    };
                    handler.post(updateTask);


                    break;
            }
        }
    }
    protected class NetConn extends Thread {
        private BufferedReader in;
        private Handler toClientHandler;
        private boolean isRunning;
        public NetConn(Handler myHandler) {
            this.toClientHandler = myHandler;
            this.isRunning = true;
        }

        @Override
        public void run() {

            try {
                // 创建socket对象
                socket = new Socket();
                // connect, 要保证服务器已启动
                socket.connect(new InetSocketAddress("10.0.2.2", 9999), 5000);

                // 获取socket输入输出流
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
                Log.i(TAG, "connect to server");
                writer.println("OnlineActivity connects!");

                // 启动发送消息线程
                Thread sendServerMsg = new Thread() {
                    @Override
                    public void run() {
                        while (!socket.isClosed()) {
                            if (writer != null) {
                                writer.println("selfScore" + selfScore);
                                writer.println("selfIsRunning" + selfIsRunning);
                                writer.flush();
                                Log.i(TAG, "游戏客户端正在发送消息至服务器");
                            } else {
                                Log.e(TAG, "Writer is null, cannot send message.");
                            }
                            try {
                                Thread.sleep(100); // 发送间隔
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                Log.e(TAG, "Send message thread interrupted", e);
                            }
                        }
                    }
                };

                // 接收服务器返回的数据
                Thread receiveServerMsg = new Thread() {
                    @Override
                    public void run() {
                        try {
                            String fromServer;
                            while ((fromServer = in.readLine()) != null) {
                                Log.i(TAG,fromServer);
                                if (fromServer.startsWith("other-")) {
                                    String[] parts = fromServer.split("-");
                                    if (parts.length == 3) {
                                        otherScore = Integer.parseInt(parts[1]);
                                        otherIsRunning = Boolean.parseBoolean(parts[2]);
                                        Message message = Message.obtain();
                                        message.what = 2;
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("otherScore", otherScore);
                                        bundle.putBoolean("otherIsRunning", otherIsRunning);
                                        message.setData(bundle);
                                        toClientHandler.sendMessage(message);
                                    }
                                } else if (fromServer.startsWith("ConnectionStatus")) {
                                    Message message = Message.obtain();
                                    message.what = 3;
                                    Bundle bundle = new Bundle();
                                    bundle.putBoolean("connectionStatus", true);
                                    message.setData(bundle);
                                    Log.i(TAG, "连接成功");
                                    toClientHandler.sendMessage(message);
                                }
                            }
                        } catch (IOException ex) {
                            Log.e(TAG, "Error receiving message from server", ex);
                        }
                    }
                };

                receiveServerMsg.start();
                sendServerMsg.start();
            } catch (UnknownHostException ex) {
                Log.e(TAG, "Unknown host", ex);
            } catch (IOException ex) {
                Log.e(TAG, "IO Exception", ex);
            }
        }
    }
    public void InitializeUI(){
        setContentView(R.layout.activity_record);
        textRecord = findViewById(R.id.textRecord);
        list = findViewById(R.id.ListView01);
        returnButton = findViewById(R.id.returnButton);
        textRecord.setText("联机模式排行榜");
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
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;

        map = new HashMap<>();
        map.put("排名",String.valueOf(1));
        map.put("用户名","自己");
        map.put("得分",selfScore);
        map.put("时间","0:00");
        listitem.add(map);

        map = new HashMap<>();
        map.put("排名",String.valueOf(1));
        map.put("用户名","对手");
        map.put("得分",otherScore);
        map.put("时间","0:00");
        listitem.add(map);

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