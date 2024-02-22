package hsos.prog3.sudofun.View;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Objects;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.UserEntity;
import hsos.prog3.sudofun.databinding.ActivityPlayBinding;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.Play;
import hsos.prog3.sudofun.model.UserEntity;
import hsos.prog3.sudofun.viewmodel.DataViewModel;
import hsos.prog3.sudofun.viewmodel.PlayViewModel;
import hsos.prog3.sudofun.viewmodel.SudokuCreator;
import hsos.prog3.sudofun.viewmodel.TimerViewModel;

public class PlayActivity extends AppCompatActivity {
    Play game;
    UserEntity user;
    PlayViewModel playViewModel;
    DataViewModel dataViewModel;
    String username;

    PlayGraphic graphic;

    public ActivityPlayBinding getBinding() {
        return binding;
    }

    private ActivityPlayBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getSerializable("user", UserEntity.class);
            if (user != null) {
                username = user.getUsername();
            }
        }
        int level = getIntent().getIntExtra("selectedLevel", 0);
        binding = ActivityPlayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.buttonHint.setOnClickListener(this::buttonHintClickEvent);
        binding.btnPause.setChecked(false);
        setContentView(view);
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        playViewModel = new ViewModelProvider(this).get(PlayViewModel.class);

        dataViewModel.findByName(username).observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(UserEntity retrievedUser) {
                if (retrievedUser != null) {
                    // User gefunden, initGame aufrufen
                    user = retrievedUser;
                    initGame(level);
                } else {
                    Log.w("INFOTAG", "User " + username + " not found.");
                }
            }
        });


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
    private void initGame(int level) {

        game = new Play();
        resetGameVariables();
        SudokuCreator creator = new SudokuCreator(getSelectedLevel(game, level));
        Thread threadCreator = new Thread(creator, "CreatorThread");
        threadCreator.start();
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
        graphic = new PlayGraphic(this,this, game, playViewModel);
        //graphic.setFocusedEditText(findFreeCell());
        graphic.generateGrid(binding.gridLayoutSudoku,binding.gridLayoutMask ,binding.playScreen);
        game.getTimer().start();
        Thread threadTimer = new Thread(game.getTimer().getTimerRunnable());
        threadTimer.start();
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.loadedGameView.setVisibility(View.VISIBLE);
    }


    private void resetGameVariables() {
        game.reset();
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
        if (bestTime != 0) {
            int secondsTemp = ((int) (bestTime / 1000));
            int minutes = secondsTemp / 60;
            int seconds = secondsTemp - minutes * 60;
            String time = String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
            binding.textViewTimerOld.setText(time);
        }
    }

    private void updateUser(@NonNull Level level){
        switch (level){
            case EASY:
                if(user.highscoreEasy == 0 || game.getTimer().getMillisSinceStart() < user.highscoreEasy) {
                    user.highscoreEasy = game.getTimer().getMillisSinceStart();
                }
                user.gamesEasy++;
                break;
            case MEDIUM:
                if(user.highscoreMedium == 0 || game.getTimer().getMillisSinceStart() < user.highscoreMedium) {
                    user.highscoreMedium = game.getTimer().getMillisSinceStart();
                }
                user.gamesMedium++;
                break;
            case HARD:
                if(user.highscoreHard == 0 || game.getTimer().getMillisSinceStart() < user.highscoreHard) {
                    user.highscoreHard = game.getTimer().getMillisSinceStart();
                }
                user.gamesHard++;
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
    public void endGame(){
        updateUser(game.getLevel());
        Log.w("INFOTAG", "Model is: " + game.dataViewModel);
        dataViewModel.updateUserDB(user);
        Bundle bundle = new Bundle();
        bundle.putString("level", game.getLevel().name());
        bundle.putLong("time", game.getTimer().getMillisSinceStart());
        bundle.putSerializable("user", user);

        Intent intent = new Intent(PlayActivity.this, StatisticActivity.class);
        intent.putExtras(bundle);

        finish();
        startActivity(intent);
    }

    private void buttonHintClickEvent(View view){
        int id = game.getHelper().getRandomFreeCell(game.getField(), game.getSolvedField());
        EditText editText = findViewById(id);
        game.getHelper().numberToCoordinate(id, game);
        int value = game.getSolvedField()[game.getRowHint()][game.getColumnHint()];
        editText.setTextColor(Color.BLACK);
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
            List<EditText> editTexts = graphic.getEditTexts();
            if (isChecked) {
                binding.buttonMode.setBackgroundResource(R.drawable.btn_primary_toggled);
                if(playViewModel.getLastFocusedCell() != null){
                    playViewModel.getLastFocusedCell().setBackgroundResource(R.drawable.edit_text_field_border_black);
                }
                if(game.getTimer().isRunning()) {

                    for (GridLayout grid : noteGrids) {
                        grid.setEnabled(true);
                        grid.setVisibility(View.VISIBLE);
                    }
                    for (EditText editText : editTexts) {
                        editText.setEnabled(false);
                    }
                }
                game.setNoteMode(true);
            } else {
                binding.buttonMode.setBackgroundResource(R.drawable.btn_primary);
                if(playViewModel.getLastFocusedGrid() != null){
                    playViewModel.getLastFocusedGrid().setBackgroundResource(R.drawable.edit_text_field_border_black);
                }
                for (GridLayout grid : noteGrids) {
                    grid.setEnabled(false);
                    grid.setVisibility(View.INVISIBLE);
                }
                for(EditText editText : editTexts){
                    editText.setEnabled(true);
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
        List<EditText> editTexts = graphic.getEditTexts();
        List<GridLayout>  noteGrids = graphic.getNoteGrids();
        if (isChecked) {
            buttonView.setBackgroundResource(R.drawable.icon_play);
            game.getTimer().pause();
            for(EditText editText : editTexts){
                editText.setEnabled(false);
                binding.gridLayoutMask.setVisibility(View.VISIBLE);
            }
            for (GridLayout grid : noteGrids) {
                grid.setEnabled(false);
                grid.setVisibility(View.INVISIBLE);
            }

        } else {
            buttonView.setBackgroundResource(R.drawable.icon_pause);
            game.getTimer().startAfterPause();
            for(EditText editText : editTexts){
                editText.setEnabled(true);
                binding.gridLayoutMask.setVisibility(View.INVISIBLE);
            }
            for (GridLayout grid : noteGrids) {
                grid.setEnabled(true);
                grid.setVisibility(View.VISIBLE);
            }
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