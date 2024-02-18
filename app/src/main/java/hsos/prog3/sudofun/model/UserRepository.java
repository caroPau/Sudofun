package hsos.prog3.sudofun.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import hsos.prog3.sudofun.database.AppDatabase;
import hsos.prog3.sudofun.database.UserDAO;
import hsos.prog3.sudofun.database.UserEntity;

public class UserRepository {
    private UserDAO userDAO;
    private UserEntity user;
    private int count;
    private LiveData<List<UserEntity>> highscoresEasy;
    private LiveData<List<UserEntity>> highscoresMedium;
    private LiveData<List<UserEntity>> highscoresHard;

    public UserRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.userDAO();
    }

    public UserEntity findByName(String username){
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

    public LiveData<List<UserEntity>> getHighscoresEasy(){
        AppDatabase.databaseWriter.execute(() ->{
            highscoresEasy = userDAO.getEasyHighscores();
        });
        return highscoresEasy;
    }

    public LiveData<List<UserEntity>> getHighscoresMedium(){
        AppDatabase.databaseWriter.execute(() ->{
            highscoresMedium = userDAO.getMediumHighscores();
        });
        return highscoresMedium;
    }

    public LiveData<List<UserEntity>> getHighscoresHard(){
        AppDatabase.databaseWriter.execute(() ->{
            highscoresHard = userDAO.getHardHighscores();
        });
        return highscoresHard;
    }

}
