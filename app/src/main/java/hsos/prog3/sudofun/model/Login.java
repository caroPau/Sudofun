package hsos.prog3.sudofun.model;

import android.widget.EditText;

import java.util.List;

import hsos.prog3.sudofun.database.AppDatabase;
import hsos.prog3.sudofun.database.UserEntity;

public class Login {

    private EditText input_username; //Textfeld für die Eingabe des Namens
    private List<UserEntity> users;
    public static AppDatabase db;

    /**
     *  Getter
     */
    public EditText getInput_username() {
        return input_username;
    }
    public List<UserEntity> getUsers() {
        return users;
    }

    /**
     *  Setter
     */
    public void setInput_username(EditText input_username) {
        this.input_username = input_username;
    }
    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

}
