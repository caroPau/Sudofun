package hsos.prog3.sudofun.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.Play;
import hsos.prog3.sudofun.model.User;

public class PlayActivity extends AppCompatActivity {
    Play game;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Bundle bundle = getIntent().getExtras();
        PlayGraphic graphic = new PlayGraphic();
        user = bundle.getSerializable("user", User.class);
        int level = bundle.getInt("selectedLevel", 0);
        initGame(level);
        graphic.generateGrid(this,game.getField(),findViewById(R.id.gridLayoutSudoku));
        Button buttonHint = findViewById(R.id.buttonHint);
        Button buttonMode = findViewById(R.id.buttonMode);
        buttonHint.setOnClickListener(this::buttonHintClickEvent);
        buttonMode.setOnClickListener(this::buttonModeClickEvent);
        ToggleButton buttonPause = findViewById(R.id.btnPause);
        buttonPause.setChecked(false);

        buttonPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                togglePauseButton(isChecked, buttonView);
            }
        });
    }

    private Level getSelectedLevel(Play game, int lvl) {
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

    private void setDigit(int[][] field, int digit, int row, int column){
       if(game.getHelper().isCoordinateEditable(game.getOccupiedCells(), row, column)){
           field[row][column] = digit;
       }else{
           Toast.makeText(this,"Dieses Feld kann nicht geändert werden!", Toast.LENGTH_SHORT).show();
       }
    }

    /**
     * Erstellt ein neues Spiel und initialisiert dessen Variablen, erstellt neues Spielfeld und startet den Timer
     * @param level Der gewünschte Schwierigkeitsgrad
     *
     * @author C. Paul
     */
    private void initGame(int level){
        game = new Play();
        SudokuCreator creator = new SudokuCreator(getSelectedLevel(game, level));
        game.setField(creator.createSudoku(getSelectedLevel(game, level)));
        game.setSolvedField(creator.getSolvedField());
        game.setTimer(new TimerViewModel());
        if(game.getHelper().getOccupiedCells(game.getField()) != null) {
            game.setOccupiedCells(game.getHelper().getOccupiedCells(game.getField()));
            game.setOpenCells(81 - game.getOccupiedCells().size());
        }
        Thread thread = new Thread(game.getTimer().getTimer());
        thread.start();
        game.getTimer().start();
    }

    /**
     * Aktualisiert bei Bedarf den Highscore des Spielers und inkrementiert die gespielten Spiele
     *
     * @author C. Paul
     */
    private void updateBestTime(){
        switch (game.getLevel()){
            case EASY:
                if(user.getBestTimeEasy() > game.getTimer().getTimer().getMillisSinceStart()){
                    user.setBestTimeEasy(game.getTimer().getTimer().getMillisSinceStart());
                }
                user.setEasyGames(user.getEasyGames() + 1);
                break;
            case MEDIUM:
                if(user.getBestTimeMedium() > game.getTimer().getTimer().getMillisSinceStart()){
                    user.setBestTimeMedium(game.getTimer().getTimer().getMillisSinceStart());
                }
                user.setMediumGames(user.getEasyGames() + 1);
                break;
            case HARD:
                if(user.getBestTimeHard() > game.getTimer().getTimer().getMillisSinceStart()){
                    user.setBestTimeHard(game.getTimer().getTimer().getMillisSinceStart());
                }
                user.setHardGames(user.getHardGames() + 1);
                break;
            default:
                break;
        }
    }



    /**
     * Ruft die Methode @updateBestTime auf, erstellt das Bundle und den Intent für die nächste Activity und startet diese
     *
     * @author C. Paul
     */
    private void endGame(){
        updateBestTime();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        bundle.putString("level", game.getLevel().name());
        Intent intent = new Intent(PlayActivity.this, StatisticActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void buttonHintClickEvent(View view){
        SudokuHelper helper = new SudokuHelper();
        helper.getRandomFreeCell(game.getField(), game.getSolvedField());
        //TODO: Ansicht aktualisieren
    }

    private void buttonModeClickEvent(View view){

    }

    /**
     * Ermöglicht das Pausieren und Restarten des Timers
     *
     * @author M. Paul
     */
    private void togglePauseButton(boolean isChecked, CompoundButton buttonView) {
        if (!isChecked) {
            buttonView.setBackgroundResource(R.drawable.icon_play);
            game.getTimer().pause();
            //TODO: Alle EditText Zellen deaktivieren
        } else {
            buttonView.setBackgroundResource(R.drawable.icon_pause);
            game.getTimer().startAfterPause();
        }
    }

    /**
     * Ermöglicht das navigieren zum Menü.
     *
     * @author M. Paul
     */
    public void navigateBack(View view) {
        this.finish();
    }


}
