package hsos.prog3.sudofun.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.databinding.ActivityLoginBinding;
import hsos.prog3.sudofun.model.UserEntity;
import hsos.prog3.sudofun.viewmodel.DataViewModel;
import hsos.prog3.sudofun.viewmodel.LoginViewModel;

/**
 *  Logik für die LoginView
 */
public class LoginActivity extends AppCompatActivity {
    private DataViewModel dataViewModel;
    private EditText input_username;
    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        input_username = this.findViewById(R.id.inputUsername);
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        hsos.prog3.sudofun.databinding.ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        input_username = binding.inputUsername;
        binding.buttonLogin.setOnClickListener(this::loginButtonClickEvent);

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
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
    private void loginButtonClickEvent(View view) {
        String username = input_username.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this, "Bitte gib einen Namen ein um fortzufahren!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(LoginActivity.this, LevelActivity.class);
            Bundle bundle = new Bundle();

            dataViewModel.findByName(username).observe(this, new Observer<UserEntity>() {
                @Override
                public void onChanged(UserEntity retrievedUser) {
                    if (retrievedUser == null) {
                        // Benutzer nicht gefunden, erstellen Sie einen neuen Benutzer und fügen Sie ihn hinzu
                        loginViewModel.setUserEntity(new UserEntity(username, 0, 0, 0, 0, 0, 0));
                        dataViewModel.insertAll(loginViewModel.getUserEntity());
                    } else {
                        loginViewModel.setUserEntity(retrievedUser);
                    }

                    bundle.putSerializable("user", loginViewModel.getUserEntity());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }
}
