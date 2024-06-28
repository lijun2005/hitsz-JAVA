package com.example.aircraftwar2024.scorePattern;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class UserDaoImpl implements UserDao {
    private ArrayList<User> users;
    private Context context;
    private FileInputStream fileIn;
    private FileOutputStream fileOut;

    public UserDaoImpl(String fileName, Context context) {
        this.context = context;
        try {
            fileIn = this.context.openFileInput(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            this.users = (ArrayList<User>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            // 文件不存在时，初始化空的用户列表
            this.users = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            this.users = new ArrayList<>();
        }
        try {
            fileOut = this.context.openFileOutput(fileName, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> getAllUser() {
        return this.users;
    }

    @Override
    public void AddUser(User user) {
        this.users.add(user);
    }

    @Override
    public void DeleteUser(String userTime) {
        for (User user : users) {
            if (user.getUserTime().equals(userTime)) {
                users.remove(user);
                return;
            }
        }
    }

    @Override
    public void save() {
        try {
            Collections.sort(users);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(users);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
