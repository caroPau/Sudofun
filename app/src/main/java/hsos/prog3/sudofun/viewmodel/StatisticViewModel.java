package hsos.prog3.sudofun.viewmodel;

import static hsos.prog3.sudofun.model.Level.EASY;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.Statistic;
import hsos.prog3.sudofun.model.UserEntity;

public class StatisticViewModel extends AndroidViewModel {
    private Statistic statistic;

    public StatisticViewModel(@NonNull Application application) {
        super(application);
        statistic = new Statistic();
    }

    public void setUser(UserEntity user) {
        statistic.setUser(user);
    }

    public UserEntity getUser() {
        return statistic.getUser();
    }

    public void setLevel(Level level){
        statistic.setLevel(level);
    }

    public Level getLevel(){
        return statistic.getLevel();
    }
    public List<UserEntity> getBestUsers(){
        return statistic.getBestUsers();
    }

    public void setBestUsers(List<UserEntity> bestUsers){
        statistic.setBestUsers(bestUsers);
    }

    public LiveData<List<UserEntity>> getStatistics(DataViewModel dataViewModel) {
        LiveData<List<UserEntity>> users ;
        switch (getLevel()) {
            case EASY:
                users = dataViewModel.getHighscoresEasy();
                break;
            case MEDIUM:
                users = dataViewModel.getHighscoresMedium();
                break;
            case HARD:
                users = dataViewModel.getHighscoresHard();
                break;
            default:
                users = null;
        }
        return users;
    }
}
