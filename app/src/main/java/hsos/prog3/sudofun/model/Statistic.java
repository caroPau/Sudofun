package hsos.prog3.sudofun.model;

import java.util.List;

/**
 * @author M.Paul
 */
public class Statistic {
    private UserEntity user;
    private LevelEnum levelEnum;
    private List<UserEntity> bestUsers;


    public Statistic(){
        this.user = null;
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

}


