package hsos.prog3.sudofun.viewmodel;

import static hsos.prog3.sudofun.model.Login.db;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.databinding.ActivityLevelBinding;
import hsos.prog3.sudofun.databinding.ActivityLoginBinding;
import hsos.prog3.sudofun.database.AppDatabase;
import hsos.prog3.sudofun.database.UserEntity;
import hsos.prog3.sudofun.databinding.ActivityLoginBinding;
import hsos.prog3.sudofun.model.Login;
import hsos.prog3.sudofun.model.User;

/**
 *  Logik für die LoginView
 */
public class LoginActivity extends AppCompatActivity {
    private Login login;
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = new Login();
        login.setInput_username(this.findViewById(R.id.inputUsername));
        login.dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        login.setInput_username(binding.inputUsername);
        binding.btnLogin.setOnClickListener(this::loginButtonClickEvent);
    }

    /**
     * Logik für das ClickEvent auf den Login-Button
     * Prüft ob ein Spieler schon bekannt ist, wenn nicht legt er einen neuen User an
     * Erstellt den Intent für die nächste Activity und startet diese
     *
     * @param view Die aktuelle View auf der auch der Button ist
     *
     * @author C. Paul
     */
    private void loginButtonClickEvent(View view){
        String username = login.getInput_username().getText().toString();
        UserEntity player = null;
        boolean isKnown = false;
        if (username.isEmpty()) {
            Toast.makeText(this, "Bitte gib einen Namen ein um fortzufahren!", Toast.LENGTH_SHORT).show();
        } else {
            if (login.getUsers() != null) {
                for (UserEntity user : login.getUsers()) {
                    if (Objects.equals(user.username, username)) {
                        isKnown = true;
                        break;
                    }
                }
                if (!isKnown) {
                    player = new UserEntity(username, 0, 0,0, 0, 0, 0);
                    login.getUsers().add(player);
                }
            } else {
                login.setUsers(new ArrayList<>());
                player = new UserEntity(username, 0, 0, 0, 0, 0, 0);
                login.getUsers().add(player);
            }
            if(player != null) {
                db.userDAO().insertAll(player);
            }else{
                db.userDAO().insertAll(new UserEntity(username, 0, 0, 0, 0, 0, 0));
            }
            Intent intent = new Intent(LoginActivity.this, LevelActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
    }
}
