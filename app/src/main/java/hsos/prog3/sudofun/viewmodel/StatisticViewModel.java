package hsos.prog3.sudofun.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

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

    public List<UserEntity> getBestUsers(){
        return statistic.getBestUsers();
    }

    public void setBestUsers(List<UserEntity> bestUsers){
        statistic.setBestUsers(bestUsers);
    }
}
