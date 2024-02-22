/**
 *
 * @author M.Paul
 *
 */
package hsos.prog3.sudofun.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.databinding.ActivityLevelBinding;
import hsos.prog3.sudofun.model.UserEntity;

public class LevelActivity extends AppCompatActivity {
    private int selectedLevel = -1;
    private ActivityLevelBinding binding;
    UserEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        binding = ActivityLevelBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getSerializable("user", UserEntity.class);
            Log.w("INFOTAG", "LevelActivity: username" + user.getUsername());
        }
    }

    public void selectLevel(View view) {
        int buttonId = view.getId();
        binding.buttonEasy.setBackgroundResource(R.drawable.btn_secondary);
        binding.buttonMedium.setBackgroundResource(R.drawable.btn_secondary);
        binding.buttonHard.setBackgroundResource(R.drawable.btn_secondary);
        view.setBackgroundResource(R.drawable.btn_primary);

        if (buttonId == R.id.buttonEasy) {
            selectedLevel = 0;
        } else if (buttonId == R.id.buttonMedium) {
            selectedLevel = 1;
        } else if (buttonId == R.id.buttonHard) {
           selectedLevel = 2;
        }
    }


    public void startNextActivity(View view) {
        if(selectedLevel != -1) {
            Intent intent = new Intent(LevelActivity.this, PlayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("selectedLevel", selectedLevel);
            bundle.putSerializable("user", user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Bitte wähle eine Schwierigkeit um fortzufahren!", Toast.LENGTH_SHORT).show();
        }
    }

    public void navigateLogout(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //FLAG entfernt alle Activities im Task BackStack, sodass nach dem Logout nicht zurück navigiert werden kann
        startActivity(intent);
        finishAffinity();
    }
}
