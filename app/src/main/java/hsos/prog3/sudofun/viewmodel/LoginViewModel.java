package hsos.prog3.sudofun.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import hsos.prog3.sudofun.model.Login;

public class LoginViewModel extends AndroidViewModel {

    private final Login login;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        login = new Login();
    }

    public void setDataViewModel(DataViewModel dataViewModel){
        login.setDataViewModel(dataViewModel);
    }

}
