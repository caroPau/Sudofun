package hsos.prog3.sudofun.model;

import java.util.List;
import java.util.Map;

import hsos.prog3.sudofun.viewmodel.DataViewModel;

public class Statistic {
    private UserEntity user;
    private LevelEnum levelEnum;
    private int totalGames;
    private long bestTime;
    private DataViewModel dataViewModel;
    private Map<UserEntity, Long> highscores;
    private List<UserEntity> bestUsers;


    public Statistic(){
        this.user = null;
        totalGames = 0;
        bestTime = 0;
        dataViewModel = null;
    }

    public LevelEnum getLevel(){
        return levelEnum;
    }

    public void setLevel(LevelEnum levelEnum){
        this.levelEnum = levelEnum;
    }

    public List<UserEntity> getBestUsers() {
        return bestUsers;
    }

    public void setBestUsers(List<UserEntity> bestUsers) {
        this.bestUsers = bestUsers;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setHighscores(Map<UserEntity, Long> highscores) {
        this.highscores = highscores;
    }
}


