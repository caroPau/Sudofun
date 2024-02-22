package hsos.prog3.sudofun.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    private final UserDAO userDAO;
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
        return userDAO.findByName(username);
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
