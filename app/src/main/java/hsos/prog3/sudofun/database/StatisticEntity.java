package hsos.prog3.sudofun.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "statistics")
public class StatisticEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "player_name")
    private String playerName;

    @ColumnInfo(name = "time")
    private long timeInMillis;

    public StatisticEntity(String playerName, long timeInMillis) {
        this.playerName = playerName;
        this.timeInMillis = timeInMillis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}

