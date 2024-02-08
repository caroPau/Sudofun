package hsos.prog3.sudofun.viewmodel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Level;


public class PlayActivity extends AppCompatActivity {
    private Level level;
    private static int[][] field;
    private TimerViewModel timer;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        field = SudokuCreator.createSudoku(getSelectedLevel());
        handler = new Handler(Looper.myLooper());
        timer = new TimerViewModel();
        timer.start();
    }

    private Level getSelectedLevel() {
        int lvl = getIntent().getIntExtra("selectedLevel", 0);
        switch (lvl) {
            case 0:
                level = Level.EASY;
                break;
            case 1:
                level = Level.MEDIUM;
                break;
            case 2:
                level = Level.HARD;
                break;
            default:
                // TODO Fehlerbehandlung
                break;
        }
        return level;
    }

    private static void setDigit(int digit, int row, int column){
        if(SudokuHelper.isValid(row, column, digit, field)){
            field[row][column] = digit;
        }else{
            //TODO: Visueller Effekt
        }
    }

}
