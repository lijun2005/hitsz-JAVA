package edu.hitsz.scorepattern;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private ArrayList<User> users;
    private Path savepath; // 保存记录的原始数据
    private Path filePath; // 保存排行榜打印数据
    // 输入一个对象保存路径，首先判断路径存在与否，若存在，读取当前路径文件，若不存在创建一个文件。

    public UserDaoImpl(Path path) {
        if (Files.exists(path)) {
            try {
                FileInputStream fileIn = new FileInputStream(path.toString());
                ObjectInputStream objectIN = new ObjectInputStream(fileIn);
                this.users = (ArrayList<User>) objectIN.readObject();
                objectIN.close();
                fileIn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.users = new ArrayList<User>();
        }
        this.savepath = path;
        Path directory = this.savepath.getParent();
        this.filePath = directory.resolve("score.txt");
    }

    @Override
    public ArrayList<User> getAllUser() {
        return this.users;
    }

    @Override
    public void AddUser(User user) {
        this.users.add(user);
        System.out.println("Add User successfully!");
    }

    @Override
    public void DeleteUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                users.remove(user);
                System.out.println("Delete User " + username + "successfully !");
                return;
            }
        }
        System.out.println("No" + username + "!");
    }

    @Override
    public User findUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {

                System.out.println("Find User " + username + "successfully !");
                return user;
            }
        }
        System.out.println("No" + username + "!");
        return null;
    }

    @Override
    public void save() {
        try {
            Collections.sort(users);
            FileOutputStream fileOut = new FileOutputStream(this.savepath.toString());
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(users);
            objectOut.close();
            fileOut.close();
            System.out.println("保存数据成功！");
        } catch (IOException e) {
            System.out.println("保存数据时出错：" + e.getMessage());
        }

        try {
            Files.write(filePath, Collections.emptyList(), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            Collections.sort(users);
            for (User user : users) {
                Files.writeString(filePath, user.toString() + System.lineSeparator(),
                        StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.out.println("保存排行榜数据出错！");
        }
    }

    @Override
    public void printScore() {
        System.out.println("********************************************");
        System.out.println("                      排行榜                   ");
        System.out.println("********************************************");
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath.toString()))) {
            String line;
            int lineNumber = 1; // 记录行号
            while ((line = br.readLine()) != null) {
                System.out.println("第 " + lineNumber + " 名: " + line);
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
