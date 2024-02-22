package hsos.prog3.sudofun.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import hsos.prog3.sudofun.model.Login;

/**
 * ViewModel für Login-Ansicht.
 */
public class LoginViewModel extends AndroidViewModel {

    private Login login;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        login = new Login();
    }

    public void setDataViewModel(DataViewModel dataViewModel){
        login.setDataViewModel(dataViewModel);
    }
}
