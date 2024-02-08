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


public class LevelActivity extends AppCompatActivity {
    private int selectedLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Button btn_easy = findViewById(R.id.btnEasy);
        Button btn_medium = findViewById(R.id.btnMedium);
        Button btn_hard = findViewById(R.id.btnHard);


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
        intent.putExtra("selectedLevel", selectedLevel);
        startActivity(intent);
    }


}
