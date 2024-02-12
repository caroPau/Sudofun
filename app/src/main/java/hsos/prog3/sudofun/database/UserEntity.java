package hsos.prog3.sudofun.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "games_easy")
    public int gamesEasy;

    @ColumnInfo(name = "highscore_easy")
    public long highscoreEasy;

    @ColumnInfo(name = "games_medium")
    public int gamesMedium;

    @ColumnInfo(name = "highscore_medium")
    public long highscoreMedium;

    @ColumnInfo(name = "games_hard")
    public int gamesHard;

    @ColumnInfo(name = "highscore_hard")
    public long highscoreHard;

    public UserEntity(@NonNull String username){
        this.username = username;
        this.gamesEasy = 0;
        this.gamesMedium = 0;
        this.gamesHard = 0;
        this.highscoreEasy = 0;
        this.highscoreMedium = 0;
        this.highscoreHard = 0;
    }
}
