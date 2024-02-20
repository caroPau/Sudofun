package hsos.prog3.sudofun.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.databinding.ActivityLoginBinding;
import hsos.prog3.sudofun.model.Login;
import hsos.prog3.sudofun.viewmodel.DataViewModel;
import hsos.prog3.sudofun.viewmodel.LoginViewModel;

/**
 *  Logik für die LoginView
 */
public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private EditText input_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        input_username = this.findViewById(R.id.inputUsername);
        loginViewModel.setDataViewModel(new ViewModelProvider(this).get(DataViewModel.class));
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        input_username = binding.inputUsername;
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
        String username = input_username.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this, "Bitte gib einen Namen ein um fortzufahren!", Toast.LENGTH_SHORT).show();
        }
            Intent intent = new Intent(LoginActivity.this, LevelActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
}
