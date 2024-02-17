package hsos.prog3.sudofun.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import hsos.prog3.sudofun.database.AppDatabase;
import hsos.prog3.sudofun.database.UserDAO;
import hsos.prog3.sudofun.database.UserEntity;

public class UserRepository {
    private UserDAO userDAO;
    private LiveData<List<UserEntity>> userList;
    private LiveData<UserEntity> user;
    private int count;

    public UserRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.userDAO();
        userList = userDAO.getAll();
    }

    public LiveData<List<UserEntity>> getAll(){
        return userList;
    }

    public LiveData<UserEntity> findByName(String username){
        AppDatabase.databaseWriter.execute(() ->{
             user = userDAO.findByName(username);
        });
        return user;
    }

    public int countByName(String username){
        AppDatabase.databaseWriter.execute(() ->{
            count = userDAO.countByName(username);
        });
        return count;
    }

    public void updateUser(UserEntity user){
        AppDatabase.databaseWriter.execute(() ->{
            userDAO.updateUser(user);
        });
    }

    public void insertAll(UserEntity...user){
        AppDatabase.databaseWriter.execute(() ->{
            userDAO.insertAll(user);
        });
    }

    public void delete(UserEntity user){
        AppDatabase.databaseWriter.execute(() ->{
            userDAO.delete(user);
        });
    }

}
