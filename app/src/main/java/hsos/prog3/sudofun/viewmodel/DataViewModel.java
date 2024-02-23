package hsos.prog3.sudofun.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hsos.prog3.sudofun.model.UserEntity;
import hsos.prog3.sudofun.model.UserRepository;

/**
 * ViewModel für Benutzerdaten (UserEntity). Hält Referenz auf ein UserRepository.
 *
 * @author C.Paul
 */
public class DataViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public DataViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<UserEntity> findByName(String username) {
        return userRepository.findByName(username);
    }

    public void updateUserDB(UserEntity user) {
        userRepository.updateUser(user);
    }

    public void insertAll(UserEntity... user) {
        userRepository.insertAll(user);
    }

    public LiveData<List<UserEntity>> getHighscoresEasy() {
        return userRepository.getHighscoresEasy();
    }

    public LiveData<List<UserEntity>> getHighscoresMedium() {
        return userRepository.getHighscoresMedium();
    }

    public LiveData<List<UserEntity>> getHighscoresHard() {
        return userRepository.getHighscoresHard();
    }
}
