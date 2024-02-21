package hsos.prog3.sudofun.model;

public class Highscore {
    private UserEntity user;
    private long time;

    public Highscore(UserEntity user, long time) {
        this.user = user;
        this.time = time;
    }
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
