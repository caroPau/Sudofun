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

import androidx.appcompat.app.AppCompatActivity;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.User;


public class LevelActivity extends AppCompatActivity {
    private int selectedLevel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Button btn_easy = findViewById(R.id.btnEasy);
        Button btn_medium = findViewById(R.id.btnMedium);
        Button btn_hard = findViewById(R.id.btnHard);
        user = getIntent().getSerializableExtra("user", User.class);


        btn_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLevel = 0;
                startNextActivity();
            }
        });


        btn_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLevel = 1;
                startNextActivity();
            }
        });


        btn_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLevel = 2;
                startNextActivity();
            }
        });
    }

    private void startNextActivity() {

        Intent intent = new Intent(LevelActivity.this, PlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("selectedLevel", selectedLevel);
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
