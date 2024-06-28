package com.example.lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyClass {
    public static void main(String[] args){
        new MyClass();
    }
    private  static  final int PORT=9999;
    private  static final int MAX_CLIENTS = 2;
    private static List<ServerSocketThread> servers = new ArrayList<>();
    private boolean connectStatus;

    public MyClass(){
        try {
            // 1. 创建ServerSocket
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket server = serverSocket.accept();
                synchronized (server){
                    if(servers.size()<MAX_CLIENTS){
                        ServerSocketThread  handler = new ServerSocketThread(server);
                        servers.add(handler);
                        handler.start();
                    }
                    if(servers.size()==MAX_CLIENTS){
                        connectStatus = true;
                        for(ServerSocketThread cl: servers)
                            cl.sendConnectionStatus(true);
                    }
                    System.out.println(servers.size());
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public class ServerSocketThread extends Thread {
        private BufferedReader in;
        private PrintWriter pw;
        private Socket socket;

        private boolean otherIsRunning;
        private boolean selfIsRunning;
        private int selfScore;
        private int otherScore;

        public ServerSocketThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                pw = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
                System.out.println("pw初始化成功");

                // 启动接收消息线程
                Thread receiveThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String content;
                        try {
                            while ((content = in.readLine()) != null) {
                                System.out.println(content);
                                handleClientMessage(content);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                // 启动发送消息线程
                Thread sendThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!socket.isClosed()) {
                            pw.println("other-" + otherScore + "-" + otherIsRunning);
                            pw.flush();
                            System.out.println("send successfully!");
                            try {
                                Thread.sleep(100); // 每500毫秒发送一次消息
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                            }
                        }
                    }
                });

                receiveThread.start();
                sendThread.start();

//                sendConnectionStatus(true);

                // 等待接收线程和发送线程完成
                receiveThread.join();
                sendThread.join();

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void handleClientMessage(String message) {
            if (message.startsWith("selfScore")) {
                selfScore = Integer.parseInt(message.substring(9));
            } else if (message.startsWith("selfIsRunning")) {
                selfIsRunning = Boolean.parseBoolean(message.substring(13));
            }
            sendToOtherClient();
        }

        public void sendConnectionStatus(boolean status) {
            pw.println("ConnectionStatus:" + status);
        }

        private void sendToOtherClient() {
            synchronized (servers) {
                for (ServerSocketThread ser : servers) {
                    if (ser != this) {
                        ser.otherScore = selfScore;
                        ser.otherIsRunning = selfIsRunning;
                    }
                }
            }
        }
    }
}