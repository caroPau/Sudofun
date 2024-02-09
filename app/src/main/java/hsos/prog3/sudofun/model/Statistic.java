package hsos.prog3.sudofun.model;

import java.util.HashSet;

public class Statistic {
    User user;
    int totalGames;
    int bestTime;
    HashSet<User> highScores;

    public Statistic(User user){
        this.user = user;
        totalGames = 0;
        bestTime = 0;
        highScores = null;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getBestTime() {
        return bestTime;
    }

    public void setBestTime(int bestTime) {
        this.bestTime = bestTime;
    }

    public HashSet<User> getHighScores() {
        return highScores;
    }

    public void setHighScores(HashSet<User> highScores) {
        this.highScores = highScores;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}


