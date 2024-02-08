package hsos.prog3.sudofun.viewmodel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.Play;


public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Play game = new Play();
        game.setField(SudokuCreator.createSudoku(getSelectedLevel(game)));
        game.setTimer(new TimerViewModel());
        game.getTimer().start();
    }

    private Level getSelectedLevel(Play game) {
        int lvl = getIntent().getIntExtra("selectedLevel", 0);
        switch (lvl) {
            case 0:
                game.setLevel(Level.EASY);
                break;
            case 1:
                game.setLevel(Level.MEDIUM);
                break;
            case 2:
                game.setLevel(Level.HARD);
                break;
            default:
                //TODO: Was als Default-Wert?
                break;
        }
        return game.getLevel();
    }

    private static void setDigit(int digit, int row, int column, int[][] field){
        if(SudokuHelper.isValid(row, column, digit, field)){
            field[row][column] = digit;
        }else{
            // TODO: Visueller Effekt
        }
    }

}
