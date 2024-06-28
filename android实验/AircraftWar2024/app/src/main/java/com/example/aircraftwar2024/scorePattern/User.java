package com.example.aircraftwar2024.scorePattern;

import java.io.Serializable;


public class User implements Serializable, Comparable<User> {
    private String userName;
    private int userScore;
    private String userTime;

    public User(String userName, int userScore, String userTime) {
        this.userName = userName;
        this.userScore = userScore;
        this.userTime = userTime;
    }

    public int getUserScore() {
        return this.userScore;
    }

    public void setUserScore(int userscore) {
        this.userScore = userscore;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String Name) {
        this.userName = Name;
    }

    public String getUserTime() {
        return this.userTime;
    }

    public void setUserTime(String time) {
        this.userTime = time;
    }

    @Override
    public String toString() {
        return "UserName: " + this.userName + "," + "Score: " + this.userScore + "," + "Time: " + this.userTime;
    }

    @Override
    public int compareTo(User other) {
        return Integer.compare(other.userScore, this.userScore);
    }
}
