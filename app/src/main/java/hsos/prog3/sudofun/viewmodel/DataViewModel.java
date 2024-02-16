package hsos.prog3.sudofun.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hsos.prog3.sudofun.database.UserEntity;
import hsos.prog3.sudofun.model.Repository;

public class DataViewModel extends AndroidViewModel {
    private Repository repository;
    private final LiveData<List<UserEntity>> userList;

    public DataViewModel(Application application){
        super(application);
        repository = new Repository(application);
        userList = repository.getAll();
    }

    public LiveData<List<UserEntity>> getAll(){
        return userList;
    }

    public LiveData<UserEntity> findByName(String username){
        return repository.findByName(username);
    }

    public void updateUser(UserEntity user){
        repository.updateUser(user);
    }
    public void insertAll(UserEntity...user){
        repository.insertAll(user);
    }
    public void delete(UserEntity user){
        repository.delete(user);
    }
}
