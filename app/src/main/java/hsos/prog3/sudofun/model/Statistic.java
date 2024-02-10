package hsos.prog3.sudofun.model;

import java.util.HashSet;

public class Statistic {
    User user;
    int totalGames;
    long bestTime;

    public Statistic(User user){
        this.user = user;
        totalGames = 0;
        bestTime = 0;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public long getBestTime() {
        return bestTime;
    }

    public void setBestTime(long bestTime) {
        this.bestTime = bestTime;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //TODO: Datenbank für Highscores
}


