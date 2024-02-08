/**
 *
 * @author M.Paul
 *
 */
package hsos.prog3.sudofun;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class LevelActivity extends AppCompatActivity {
    private Button btn_easy, btn_medium, btn_hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        btn_easy = this.findViewById(R.id.btnEasy);
        btn_medium = this.findViewById(R.id.btnMedium);
        btn_hard = this.findViewById(R.id.btnHard);



        btn_easy.setOnClickListener(this::levelButtonClickEvent);
    }

    private void levelButtonClickEvent(View view) {
    }
}
