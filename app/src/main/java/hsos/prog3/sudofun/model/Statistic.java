package hsos.prog3.sudofun.model;

import androidx.lifecycle.ViewModelProvider;

import java.util.HashSet;
import java.util.Map;

import hsos.prog3.sudofun.database.UserEntity;
import hsos.prog3.sudofun.viewmodel.DataViewModel;

public class Statistic {
    private UserEntity user;
    private int totalGames;
    private long bestTime;
    private DataViewModel dataViewModel;
    private Map<UserEntity, Long> highscores;


    public Statistic(){
        this.user = null;
        totalGames = 0;
        bestTime = 0;
        dataViewModel = null;
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


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public DataViewModel getDataViewModel(){
        return this.dataViewModel;
    }

    public void setDataViewModel(DataViewModel dataViewModel){
        this.dataViewModel = dataViewModel;
    }

    public Map<UserEntity, Long> getHighscores() {
        return highscores;
    }

    public void setHighscores(Map<UserEntity, Long> highscores) {
        this.highscores = highscores;
    }
}


