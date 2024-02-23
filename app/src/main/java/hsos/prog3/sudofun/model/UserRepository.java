package hsos.prog3.sudofun.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import hsos.prog3.sudofun.database.AppDatabase;
import hsos.prog3.sudofun.database.UserDAO;

/**
 * Repository für Kapselung von Datenzugriffen der UserEntity.
 *
 * @author C.Paul
 */
public class UserRepository {
    private final UserDAO userDAO;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.userDAO();
    }

    public LiveData<UserEntity> findByName(String username) {
        return userDAO.findByName(username);
    }

    public void updateUser(UserEntity user) {
        AppDatabase.databaseWriter.execute(() -> {
            userDAO.updateUser(user);
        });
    }

    public void insertAll(UserEntity... user) {
        AppDatabase.databaseWriter.execute(() -> {
            userDAO.insertAll(user);
        });
    }

    public LiveData<List<UserEntity>> getHighscoresEasy() {
        return userDAO.getEasyHighscores();
    }

    public LiveData<List<UserEntity>> getHighscoresMedium() {
        return userDAO.getMediumHighscores();
    }

    public LiveData<List<UserEntity>> getHighscoresHard() {
        return userDAO.getHardHighscores();
    }
}
