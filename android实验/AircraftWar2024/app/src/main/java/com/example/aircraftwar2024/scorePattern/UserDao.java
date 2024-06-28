package com.example.aircraftwar2024.scorePattern;

import java.util.ArrayList;

public interface UserDao {
    public ArrayList<User> getAllUser();

    void AddUser(User user);

    void DeleteUser(String username);

    void save();

}

