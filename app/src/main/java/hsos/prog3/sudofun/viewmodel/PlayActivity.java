package hsos.prog3.sudofun.viewmodel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.Play;


public class PlayActivity extends AppCompatActivity {
    Play game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        game = new Play();
        SudokuCreator creator = new SudokuCreator(getSelectedLevel(game));
        game.setField(creator.createSudoku(getSelectedLevel(game)));
        game.setSolvedField(creator.getSolvedField());
        game.setTimer(new TimerViewModel());
        game.getTimer().start();
        Button buttonHint = findViewById(R.id.buttonHint);
        Button buttonMode = findViewById(R.id.buttonMode);
        buttonHint.setOnClickListener(this::buttonHintClickEvent);
        buttonMode.setOnClickListener(this::buttonModeClickEvent);
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
                Intent intent = new Intent(PlayActivity.this, LevelActivity.class);
                startActivity(intent);
                break;
        }
        return game.getLevel();
    }

    private static void setDigit(Play game,int digit, int row, int column, int[][] field, EditText editText){
        field[row][column] = digit;
        if(!game.getHelper().isValid(row, column, digit, field)){
            editText.setTextColor(Color.RED);
        }
    }

    private void buttonHintClickEvent(View view){
        SudokuHelper helper = new SudokuHelper();
        helper.getRandomFreeCell(game.getField(), game.getSolvedField());
        //TODO: Ansicht aktualisieren
    }

    private void buttonModeClickEvent(View view){

    }

}
