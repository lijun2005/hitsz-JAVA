package edu.hitsz.scorepattern;

import java.util.ArrayList;
import java.util.List;

public interface UserDao {
    public ArrayList<User> getAllUser();

    void AddUser(User user);

    void DeleteUser(String username); 
    
    public User findUser(String username);

    void save();

    void printScore();
} 
