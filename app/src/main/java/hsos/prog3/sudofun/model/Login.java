package hsos.prog3.sudofun.model;

import android.widget.EditText;

import hsos.prog3.sudofun.viewmodel.DataViewModel;

public class Login {

    private EditText input_username; //Textfeld für die Eingabe des Namens
    public DataViewModel dataViewModel;

    /**
     *  Getter
     */
    public EditText getInput_username() {
        return input_username;
    }

    /**
     *  Setter
     */
    public void setInput_username(EditText input_username) {
        this.input_username = input_username;
    }

}
