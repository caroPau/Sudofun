package hsos.prog3.sudofun.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Entität eines Benutzers. Wird als Room Entität annotiert.
 */
@Entity
public class UserEntity implements Serializable {
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

    public UserEntity(@NonNull String username, int gamesEasy, int gamesMedium, int gamesHard, long highscoreEasy, long highscoreMedium, long highscoreHard){
        this.username = username;
        this.gamesEasy = gamesEasy;
        this.gamesMedium = gamesMedium;
        this.gamesHard = gamesHard;
        this.highscoreEasy = highscoreEasy;
        this.highscoreMedium = highscoreMedium;
        this.highscoreHard = highscoreHard;
    }

    @NonNull
    public String getUsername() {
        return username;
    }
}
