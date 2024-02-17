package hsos.prog3.sudofun.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hsos.prog3.sudofun.database.UserEntity;
import hsos.prog3.sudofun.model.UserRepository;

public class DataViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private final LiveData<List<UserEntity>> userList;

    public DataViewModel(Application application){
        super(application);
        userRepository = new UserRepository(application);
        userList = userRepository.getAll();
    }

    public LiveData<List<UserEntity>> getAll(){
        return userList;
    }

    public LiveData<UserEntity> findByName(String username){
        return userRepository.findByName(username);
    }

    public void updateUser(UserEntity user){
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
}
