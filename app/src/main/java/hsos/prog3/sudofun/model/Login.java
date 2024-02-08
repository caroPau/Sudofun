package hsos.prog3.sudofun.model;

import android.widget.EditText;

import java.util.ArrayList;

public class Login {
    private EditText input_username;
    private ArrayList<User> users;

    public EditText getInput_username() {
        return input_username;
    }

    public void setInput_username(EditText input_username) {
        this.input_username = input_username;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
