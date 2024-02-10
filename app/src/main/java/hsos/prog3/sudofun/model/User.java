package hsos.prog3.sudofun.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public User createFromParcel(Parcel in){
            return new User(in);
        }

        @Override
        public Object[] newArray(int i) {
            return new User[i];
        }
    };
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

    public User(Parcel in){
        this.name = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(this.name);
    }

    public int getTotalGames() {
        return easyGames + mediumGames + hardGames
                ;
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
