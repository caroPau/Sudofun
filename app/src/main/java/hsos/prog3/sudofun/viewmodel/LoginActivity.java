package hsos.prog3.sudofun.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Login;
import hsos.prog3.sudofun.model.User;


public class LoginActivity extends AppCompatActivity {
    Login login = new Login();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (login.getUsers() == null) {
            login.setUsers(new ArrayList<>());
        }

        login.setInput_username(this.findViewById(R.id.inputUsername));

        Button btn_login = this.findViewById(R.id.btnLogin);

        btn_login.setOnClickListener(this::loginButtonClickEvent);
    }

    private void loginButtonClickEvent(View view){
        String username = login.getInput_username().getText().toString();
        User player = null;
        boolean isKnown = false;
        if (!username.isEmpty()) {
            if (login.getUsers() != null) {
                for (User user : login.getUsers()) {
                    if (Objects.equals(user.getName(), username)) {
                        isKnown = true;
                        player = user;
                    }
                }
                if (!isKnown) {
                    player = new User(username);
                    login.getUsers().add(player);
                }
            } else {
                login.setUsers(new ArrayList<>());
                player = new User(username);
                login.getUsers().add(player);
            }
        } else {
            Toast.makeText(this, "Bitte gib einen Namen ein um fortzufahren!", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(LoginActivity.this, PlayActivity.class);
        intent.putExtra("player", player);
        startActivity(intent);
    }
}
