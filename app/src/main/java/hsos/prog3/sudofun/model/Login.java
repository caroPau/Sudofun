package hsos.prog3.sudofun.model;

import android.widget.EditText;

import hsos.prog3.sudofun.viewmodel.DataViewModel;

/**
 * Klasse repräsentiert die notwendigen Informationen für den Login.
 */
public class Login {

    private String username;
    private DataViewModel dataViewModel;

    /**
     * Getter
     */
    public String getUsername() {
        return username;
    }

    public DataViewModel getDataViewModel() {
        return dataViewModel;
    }

    /**
     * Setter
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setDataViewModel(DataViewModel dataViewModel) {
        this.dataViewModel = dataViewModel;
    }

}
