package hsos.prog3.sudofun.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable {

    private final String name;
    int easyGames;
    int mediumGames;
    int hardGames;
    long bestTimeEasy;
    long bestTimeMedium;
    long bestTimeHard;

    public User(String name){
        this.name = name;
        this.easyGames = 0;
        this.mediumGames = 0;
        this.hardGames = 0;
        this.bestTimeEasy = 0;
        this.bestTimeMedium = 0;
        this.bestTimeHard = 0;
    }

    public String getName() {
        return name;
    }

    public int getTotalGames() {
        return easyGames + mediumGames + hardGames;
    }

    public int getEasyGames() {
        return easyGames;
    }

    public void setEasyGames(int easyGames) {
        this.easyGames = easyGames;
    }

    public int getMediumGames() {
        return mediumGames;
    }

    public void setMediumGames(int mediumGames) {
        this.mediumGames = mediumGames;
    }

    public int getHardGames() {
        return hardGames;
    }

    public void setHardGames(int hardGames) {
        this.hardGames = hardGames;
    }

    public long getBestTimeEasy() {
        return bestTimeEasy;
    }

    public void setBestTimeEasy(long bestTimeEasy) {
        this.bestTimeEasy = bestTimeEasy;
    }

    public long getBestTimeMedium() {
        return bestTimeMedium;
    }

    public void setBestTimeMedium(long bestTimeMedium) {
        this.bestTimeMedium = bestTimeMedium;
    }

    public long getBestTimeHard() {
        return bestTimeHard;
    }

    public void setBestTimeHard(long bestTimeHard) {
        this.bestTimeHard = bestTimeHard;
    }
}
