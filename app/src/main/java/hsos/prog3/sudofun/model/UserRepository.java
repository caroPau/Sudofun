package hsos.prog3.sudofun.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Repository für Kapselung von Datenzugriffen der UserEntity.
 */
public class UserRepository {
    private UserDAO userDAO;
    private LiveData<UserEntity> user;
    private int count;
    private List<UserEntity> highscoresEasy;
    private List<UserEntity> highscoresMedium;
    private List<UserEntity> highscoresHard;

    public UserRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.userDAO();
    }

    public LiveData<UserEntity> findByName(String username){
/*        AppDatabase.databaseWriter.execute(() ->{
             user = userDAO.findByName(username);
             Log.w("INFOTAG", "User " + user);
        });*/
        user = userDAO.findByName(username);
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
        /*AppDatabase.databaseWriter.execute(() ->{
            highscoresEasy = userDAO.getEasyHighscores();
        });
        return highscoresEasy;*/
        return userDAO.getEasyHighscores();
    }

    public LiveData<List<UserEntity>> getHighscoresMedium(){
        /*AppDatabase.databaseWriter.execute(() ->{
            highscoresMedium = userDAO.getMediumHighscores();
        });
        return highscoresMedium;*/
        return userDAO.getMediumHighscores();
    }

    public LiveData<List<UserEntity>> getHighscoresHard(){
        /*AppDatabase.databaseWriter.execute(() ->{
            highscoresHard = userDAO.getHardHighscores();
        });
        return highscoresHard;*/
        return userDAO.getHardHighscores();
    }
}
