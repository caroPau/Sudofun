package hsos.prog3.sudofun.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.UserEntity;

public class LevelViewModel extends AndroidViewModel {

    private final Level level;

    public LevelViewModel(@NonNull Application application) {
        super(application);
        level = new Level();
    }

    public void setUser(UserEntity user){
        level.setUser(user);
    }

    public void setSelectedLevel(int selectedLevel){
        level.setSelectedLevel(selectedLevel);
    }

    public UserEntity getUser(){
        return level.getUser();
    }

    public int getSelectedLevel(){
        return level.getSelectedLevel();
    }
}
