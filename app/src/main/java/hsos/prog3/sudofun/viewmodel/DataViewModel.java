package hsos.prog3.sudofun.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hsos.prog3.sudofun.model.UserEntity;
import hsos.prog3.sudofun.model.UserRepository;

public class DataViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public DataViewModel(Application application){
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<UserEntity> findByName(String username) {
        return userRepository.findByName(username);
    }

    public void updateUserDB(UserEntity user){
        userRepository.updateUser(user);
    }
    public void insertAll(UserEntity...user){
        userRepository.insertAll(user);
    }
    public void delete(UserEntity user){
        userRepository.delete(user);
    }
    public int countByName(String username){
        return userRepository.countByName(username);
    }
    public LiveData<List<UserEntity>> getHighscoresEasy(){
        return userRepository.getHighscoresEasy();
    }
    public LiveData<List<UserEntity>> getHighscoresMedium(){
        return userRepository.getHighscoresMedium();
    }
    public LiveData<List<UserEntity>> getHighscoresHard(){
        return userRepository.getHighscoresHard();
    }
}
