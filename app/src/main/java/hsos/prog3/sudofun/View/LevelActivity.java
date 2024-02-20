/**
 *
 * @author M.Paul
 *
 */
package hsos.prog3.sudofun.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.databinding.ActivityLevelBinding;

public class LevelActivity extends AppCompatActivity {
    private int selectedLevel = -1;

    private ActivityLevelBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        binding = ActivityLevelBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void selectLevel(View view) {
        int buttonId = view.getId();
        binding.btnEasy.setBackgroundResource(R.drawable.btn_secondary);
        binding.btnMedium.setBackgroundResource(R.drawable.btn_secondary);
        binding.btnHard.setBackgroundResource(R.drawable.btn_secondary);
        view.setBackgroundResource(R.drawable.btn_primary);

        if (buttonId == R.id.btnEasy) {
            selectedLevel = 0;
        } else if (buttonId == R.id.btnMedium) {
            selectedLevel = 1;
        } else if (buttonId == R.id.btnHard) {
           selectedLevel = 2;
        }
    }


    public void startNextActivity(View view) {
        if(selectedLevel != -1) {
            Intent intent = new Intent(LevelActivity.this, PlayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("selectedLevel", selectedLevel);
            bundle.putString("username", getIntent().getStringExtra("username"));
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Bitte wähle eine Schwierigkeit um fortzufahren!", Toast.LENGTH_SHORT).show();
        }
    }
}
