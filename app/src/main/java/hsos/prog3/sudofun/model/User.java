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
    private String name;
    int totalGames;
    int easyGames;
    int mediumGames;
    int hardGames;
    float bestTimeEasy;
    float bestTimeMedium;
    float bestTimeHard;

    public User(String name){
        this.name = name;
        this.totalGames = 0;
        this.easyGames = 0;
        this.mediumGames = 0;
        this.hardGames = 0;
        this.bestTimeEasy = 0.0F;
        this.bestTimeMedium = 0.0F;
        this.bestTimeHard = 0.0F;
    }

    public User(Parcel in){
        this.name = in.readString();
        this.totalGames = in.readInt();
        this.easyGames = in.readInt();
        this.mediumGames = in.readInt();
        this.hardGames = in.readInt();
        this.bestTimeEasy = in.readFloat();
        this.bestTimeMedium = in.readFloat();
        this.bestTimeHard = in.readFloat();
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
}
