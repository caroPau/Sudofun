package hsos.prog3.sudofun.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.User;


public class LoginActivity extends AppCompatActivity {
    private static final String USER = "hsos.prog3.sudofun.viewmodel.LoginActivity.USER";
    private EditText input_username;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (users == null) {
            users = new ArrayList<>();
        }
        input_username = this.findViewById(R.id.inputUsername);

        Button btn_login = this.findViewById(R.id.btnLogin);

        btn_login.setOnClickListener(this::loginButtonClickEvent);
    }

    private void loginButtonClickEvent(View view){
        String username = input_username.getText().toString();
        User player = null;
        boolean isKnown = false;
        if (!username.isEmpty()) {
           if (users != null) {
               for (User user : users) {
                   if (Objects.equals(user.getName(), username)) {
                       isKnown = true;
                       player = user;
                   }
               }
               if (!isKnown) {
                   player = new User(username);
                   users.add(player);
               }
           } else {
               // TODO: Was wenn users == null?
           }
        } else {
            // TODO: Benachrichtigung, dass Username nicht leer sein darf
        }
        Intent intent = new Intent(LoginActivity.this, PlayActivity.class);
        intent.putExtra("player", (Parcelable) player);
    }
}
