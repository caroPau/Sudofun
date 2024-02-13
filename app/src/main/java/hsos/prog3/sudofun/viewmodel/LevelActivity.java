/**
 *
 * @author M.Paul
 *
 */
package hsos.prog3.sudofun.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.User;

public class LevelActivity extends AppCompatActivity {
    private int selectedLevel = -1;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        user = getIntent().getSerializableExtra("user", User.class);
    }

    public void selectLevel(View view) {
        int buttonId = view.getId();
        Button btn_easy = findViewById(R.id.btnEasy);
        btn_easy.setBackgroundResource(R.drawable.btn_secondary);
        Button btn_medium = findViewById(R.id.btnMedium);
        btn_medium.setBackgroundResource(R.drawable.btn_secondary);
        Button btn_hard = findViewById(R.id.btnHard);
        btn_hard.setBackgroundResource(R.drawable.btn_secondary);
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
            bundle.putSerializable("user", user);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Bitte wähle eine Schwierigkeit um fortzufahren!", Toast.LENGTH_SHORT).show();
        }
    }
}
