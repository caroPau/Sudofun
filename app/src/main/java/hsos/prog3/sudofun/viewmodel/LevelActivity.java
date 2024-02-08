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
    private Button btn_easy, btn_medium, btn_hard;
    private  String selectedLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        btn_easy = findViewById(R.id.btnEasy);
        btn_medium = findViewById(R.id.btnMedium);
        btn_hard = findViewById(R.id.btnHard);

        // Click-Listener für den "easy" Button hinzufügen
        btn_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLevel = "easy"; // Das ausgewählte Level speichern
                startNextActivity(); // Nächste Aktivität starten
            }
        });

        // Click-Listener für den "medium" Button hinzufügen
        btn_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLevel = "medium"; // Das ausgewählte Level speichern
                startNextActivity(); // Nächste Aktivität starten
            }
        });

        // Click-Listener für den "hard" Button hinzufügen
        btn_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLevel = "hard"; // Das ausgewählte Level speichern
                startNextActivity(); // Nächste Aktivität starten
            }
        });
    }

    // Methode zum Starten der nächsten Aktivität und Übergeben des ausgewählten Levels
    private void startNextActivity() {
        // Nächste Aktivität starten und das ausgewählte Level im Intent übergeben
        Intent intent = new Intent(LevelActivity.this, PlayActivity.class);
        intent.putExtra("selectedLevel", selectedLevel);
        startActivity(intent);
    }


}
