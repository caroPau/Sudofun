package hsos.prog3.sudofun;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    private static final String USER = "hsos.prog3.sudofun.LoginActivity.USER";
    private Button btn_login;
    private EditText input_username;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //input_username = this.findViewById(R.id.inputUsername);
        //btn_login = this.findViewById(R.id.btnLogin);

        btn_login.setOnClickListener(this::loginButtonClickEvent);
    }

    private void loginButtonClickEvent(View view){
        String username = input_username.getText().toString();
        User player = null;
        boolean isKnown = false;
        for(User user : users){
            if(Objects.equals(user.name, username)){
                isKnown = true;
                player = user;
            }
        }
        if(!isKnown){
            player = new User(username);
            users.add(player);
        }
        Intent intent = new Intent(LoginActivity.this, LevelActivity.class);
        intent.putExtra(USER, (Parcelable) player);
    }
}
