package hsos.prog3.sudofun.model;

import android.widget.EditText;

import hsos.prog3.sudofun.viewmodel.DataViewModel;

public class Login {

    private String username; //Textfeld für die Eingabe des Namens
    private DataViewModel dataViewModel;

    /**
     *  Getter
     */
    public String getUsername() {
        return username;
    }
    public DataViewModel getDataViewModel() {
        return dataViewModel;
    }

    /**
     *  Setter
     */
    public void setUsername(String input_username) {
        this.username = username;
    }
    public void setDataViewModel(DataViewModel dataViewModel) {
        this.dataViewModel = dataViewModel;
    }

}
