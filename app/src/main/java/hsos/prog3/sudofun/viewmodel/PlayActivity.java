package hsos.prog3.sudofun.viewmodel;

import static hsos.prog3.sudofun.model.Login.db;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.database.UserEntity;
import hsos.prog3.sudofun.databinding.ActivityPlayBinding;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.Play;

public class PlayActivity extends AppCompatActivity {
    static Play game;
    static UserEntity user;

    static PlayGraphic graphic;

    private ActivityPlayBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        String username = getIntent().getStringExtra("username");
        // user = db.userDAO().findByName(username);
        binding = ActivityPlayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.buttonHint.setOnClickListener(this::buttonHintClickEvent);
        binding.btnPause.setChecked(false);
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        graphic = new PlayGraphic(this,this);
        //user = bundle.getSerializable("user", User.class);
        int level = bundle.getInt("selectedLevel", 0);
        initGame(level);
        graphic.generateGrid(game, binding.gridLayoutSudoku, binding.playScreen);

        binding.buttonHint.setOnClickListener(this::buttonHintClickEvent);
        binding.btnPause.setChecked(false);



        binding.btnPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                togglePauseButton(isChecked, buttonView);
            }
        });

        binding.buttonMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleModeButton(isChecked);
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
        game.setFreeCells(81 - game.getLevel().getOpenCells());
        game.setSolvedField(creator.getSolvedField());
        game.setTimer(new TimerViewModel());
        game.getTimer().setActualTimerView(binding.textViewTimer);
        showBestTime();
        if(game.getHelper().getOccupiedCells(game.getField()) != null) {
            game.setOccupiedCells(game.getHelper().getOccupiedCells(game.getField()));
            game.setOpenCells(81 - game.getOccupiedCells().size());
        }
        PlayGraphic graphic = new PlayGraphic(this,this);
        graphic.generateGrid(game, binding.gridLayoutSudoku, binding.playScreen);
        game.getTimer().start();
        Thread thread = new Thread(game.getTimer().getTimerRunnable());
        thread.start();
    }

    /**
     * Aktualisiert bei Bedarf den Highscore des Spielers und inkrementiert die gespielten Spiele
     *
     * @author C. Paul
     */
    private static void updateBestTime(){
        switch (game.getLevel()){
            case EASY:
                if (user.highscoreEasy > game.getTimer().getMillisSinceStart()) {
                    user.highscoreEasy = game.getTimer().getMillisSinceStart();
                }
                user.gamesEasy++;
                break;
            case MEDIUM:
                if (user.highscoreMedium > game.getTimer().getMillisSinceStart()) {
                    user.highscoreMedium = game.getTimer().getMillisSinceStart();
                }
                user.gamesMedium++;
                break;
            case HARD:
                if (user.highscoreHard > game.getTimer().getMillisSinceStart()) {
                    user.highscoreHard = game.getTimer().getMillisSinceStart();
                }
                user.highscoreHard++;
                break;
            default:
                break;
        }
    }
    /**
     * Stellt abhängig vom aktuellen Schwierigkeitsgrad die bisherige Bestzeit dar
     *
     * @author C. Paul
     */
    private void showBestTime() {

        long bestTime = 0;
        switch (game.getLevel()) {
            case EASY:
                bestTime = user.highscoreEasy;
                break;
            case MEDIUM:
                bestTime = user.highscoreMedium;
                break;
            case HARD:
                bestTime = user.highscoreHard;
                break;
            default:
                break;
        }
        int secondsTemp = ((int) (bestTime / 1000));
        int minutes = secondsTemp / 60;
        int seconds = secondsTemp - minutes * 60;
        binding.textViewTimerOld.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
    }



    /**
     * Ruft die Methode @updateBestTime auf, erstellt das Bundle und den Intent für die nächste Activity und startet diese
     *
     * @author C. Paul
     */
    public void endGame(){
        updateBestTime();
        Bundle bundle = new Bundle();
        bundle.putString("username", user.username);
        bundle.putString("level", game.getLevel().name());
        Intent intent = new Intent(PlayActivity.this, StatisticActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void buttonHintClickEvent(View view){
        int id = game.getHelper().getRandomFreeCell(game.getField(), game.getSolvedField());
        EditText editText = findViewById(id);
        game.getHelper().numberToCoordinate(id, game);
        int value = game.getSolvedField()[game.getRowHint()][game.getColumnHint()];
        editText.setText(String.valueOf(value));
        game.setFreeCells(game.getFreeCells() - 1);
        if(game.getFreeCells() == 0){
            endGame();
        }
    }

    /**
     * Ermöglicht das Aktivieren/Deaktivieren des Notizmodus
     *
     * @author M. Paul
     */
    private void toggleModeButton(boolean isChecked) {
            List<GridLayout>  noteGrids = graphic.getNoteGrids();
            if (isChecked) {
                for (GridLayout grid : noteGrids) {
                    grid.setVisibility(View.VISIBLE);
                }
                game.setNoteMode(true);
            } else {
                for (GridLayout grid : noteGrids) {
                    grid.setVisibility(View.INVISIBLE);
                }
                game.setNoteMode(false);
            }
    }

    /**
     * Ermöglicht das Pausieren und Restarten des Timers
     *
     * @author M. Paul
     */
    private void togglePauseButton(boolean isChecked, CompoundButton buttonView) {
        if (isChecked) {
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