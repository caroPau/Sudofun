package hsos.prog3.sudofun.view;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Locale;


import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.UserEntity;
import hsos.prog3.sudofun.databinding.ActivityPlayBinding;
import hsos.prog3.sudofun.viewmodel.DataViewModel;
import hsos.prog3.sudofun.viewmodel.PlayViewModel;
import hsos.prog3.sudofun.viewmodel.SudokuCreator;
import hsos.prog3.sudofun.viewmodel.TimerViewModel;

/**
 * Activity für das Spiel mit Spielfeld.
 */
public class PlayActivity extends AppCompatActivity {
    private PlayViewModel playViewModel;
    private DataViewModel dataViewModel;
    private String username;
    private PlayGraphic graphic;

    public ActivityPlayBinding getBinding() {
        return binding;
    }

    private ActivityPlayBinding binding;

    /**
     * @author C.Paul, M.Paul
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playViewModel = new ViewModelProvider(this).get(PlayViewModel.class);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            playViewModel.setUser(bundle.getSerializable("user", UserEntity.class));
            if (playViewModel.getUser() != null) {
                username = playViewModel.getUser().getUsername();
            }
        }
        int level = getIntent().getIntExtra("selectedLevel", 0);
        binding = ActivityPlayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.buttonHint.setOnClickListener(this::buttonHintClickEvent);
        binding.buttonPause.setChecked(false);
        setContentView(view);
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        dataViewModel.findByName(username).observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(UserEntity retrievedUser) {
                if (retrievedUser != null) {
                    // User gefunden, initGame aufrufen
                    playViewModel.setUser(retrievedUser);
                    if (playViewModel.getPlayedGames() == 0) {
                        initGame(level);
                    }
                }
            }
        });


        binding.buttonHint.setOnClickListener(this::buttonHintClickEvent);
        binding.buttonPause.setChecked(false);
        binding.buttonPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

    /**
     * @author C.Paul
     */
    public void startLevelActivity() {
        Intent intent = new Intent(PlayActivity.this, LevelActivity.class);
        startActivity(intent);
    }


    /**
     * Erstellt ein neues Spiel und initialisiert dessen Variablen, erstellt neues Spielfeld und startet den Timer
     *
     * @param level Der gewünschte Schwierigkeitsgrad
     * @author C. Paul
     */
    private void initGame(int level) {
        resetGameVariables();
        SudokuCreator creator = new SudokuCreator(playViewModel.getHelper());
        Thread threadCreator = new Thread(creator, "CreatorThread");
        threadCreator.start();
        playViewModel.setField(creator.createSudoku(playViewModel.getSelectedLevel(level, this)));
        playViewModel.setFreeCells(81 - playViewModel.getLevel().getOpenCells());
        playViewModel.setSolvedField(creator.getSolvedField());
        playViewModel.setTimer(new TimerViewModel());
        playViewModel.getTimer().setActualTimerView(binding.textViewTimer);
        showBestTime();
        if (playViewModel.getHelper().getOccupiedCells(playViewModel.getField()) != null) {
            playViewModel.setOccupiedCells(playViewModel.getHelper().getOccupiedCells(playViewModel.getField()));
        }
        graphic = new PlayGraphic(this, this, playViewModel);
        graphic.generateGrid(binding.gridLayoutSudoku, binding.gridLayoutMask, binding.relativeLayoutLoadedGameView);
        playViewModel.getTimer().start();
        Thread threadTimer = new Thread(playViewModel.getTimer().getTimerRunnable(), "TimerThread");
        threadTimer.start();
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.relativeLayoutLoadedGameView.setVisibility(View.VISIBLE);
        playViewModel.setPlayedGames(playViewModel.getPlayedGames() + 1);
    }

    private void resetGameVariables() {
        playViewModel.reset();
    }

    /**
     * Stellt abhängig vom aktuellen Schwierigkeitsgrad die bisherige Bestzeit dar
     *
     * @author C. Paul
     */
    private void showBestTime() {
        long bestTime = playViewModel.getBestTime();
        if (bestTime != 0) {
            int secondsTemp = ((int) (bestTime / 1000));
            int minutes = secondsTemp / 60;
            int seconds = secondsTemp - minutes * 60;
            String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            binding.textViewTimerOld.setText(time);
        }
    }

    /**
     * @author: C.Paul
     */
    private void buttonHintClickEvent(View view) {
        int id = playViewModel.getHelper().getRandomFreeCell(playViewModel.getField(), playViewModel.getSolvedField());
        EditText editText = findViewById(id);
        playViewModel.getHelper().numberToCoordinate(id, playViewModel);
        int value = playViewModel.getSolvedField()[playViewModel.getCoordinateRow()][playViewModel.getCoordinateColumn()];
        editText.setTextColor(Color.BLACK);
        editText.setText(String.valueOf(value));
        playViewModel.setFreeCells(playViewModel.getFreeCells() - 1);
        if (playViewModel.getFreeCells() == 0) {
            endGame();
        }
    }


    /**
     * Ermöglicht das Aktivieren/Deaktivieren des Notizmodus
     *
     * @author M. Paul
     */
    private void toggleModeButton(boolean isChecked) {
        List<GridLayout> noteGrids = graphic.getNoteGrids();
        List<EditText> editTexts = graphic.getEditTexts();
        if (isChecked) {
            binding.buttonMode.setBackgroundResource(R.drawable.btn_primary_toggled);
            if (playViewModel.getLastFocusedCell() != null) {
                playViewModel.getLastFocusedCell().setBackgroundResource(R.drawable.edit_text_field_border_black);
            }
            if (playViewModel.getTimer().isRunning()) {

                for (GridLayout grid : noteGrids) {
                    grid.setEnabled(true);
                    grid.setVisibility(View.VISIBLE);
                }
                for (EditText editText : editTexts) {
                    editText.setEnabled(false);
                }
            }
            playViewModel.setNoteMode(true);
        } else {
            binding.buttonMode.setBackgroundResource(R.drawable.btn_primary);
            if (playViewModel.getLastFocusedGrid() != null) {
                playViewModel.getLastFocusedGrid().setBackgroundResource(R.drawable.edit_text_field_border_black);
            }
            for (GridLayout grid : noteGrids) {
                grid.setEnabled(false);
                grid.setVisibility(View.INVISIBLE);
            }
            for (EditText editText : editTexts) {
                editText.setEnabled(true);
            }
            playViewModel.setNoteMode(false);
        }
    }

    /**
     * Ermöglicht das Pausieren und Restarten des Timers
     *
     * @author M. Paul
     */
    private void togglePauseButton(boolean isChecked, CompoundButton buttonView) {
        List<EditText> editTexts = graphic.getEditTexts();
        List<GridLayout> noteGrids = graphic.getNoteGrids();
        if (isChecked) {
            buttonView.setBackgroundResource(R.drawable.ic_play);
            playViewModel.getTimer().pause();
            for (EditText editText : editTexts) {
                editText.setEnabled(false);
                binding.gridLayoutMask.setVisibility(View.VISIBLE);
            }
            for (GridLayout grid : noteGrids) {
                grid.setEnabled(false);
                grid.setVisibility(View.INVISIBLE);
            }

        } else {
            buttonView.setBackgroundResource(R.drawable.ic_pause);
            playViewModel.getTimer().startAfterPause();
            for (EditText editText : editTexts) {
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
     * Ruft die Methode @updateUser auf, erstellt das Bundle und den Intent für die nächste Activity und startet diese
     *
     * @author C. Paul
     */
    public void endGame() {
        playViewModel.updateUser();
        dataViewModel.updateUserDB(playViewModel.getUser());
        Bundle bundle = new Bundle();
        bundle.putString("level", playViewModel.getLevel().name());
        bundle.putLong("time", playViewModel.getTimer().getMillisSinceStart());
        bundle.putSerializable("user", playViewModel.getUser());
        Intent intent = new Intent(PlayActivity.this, StatisticActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Bricht das aktuelle Spiel ab und navigiert zum Level Menü, um ein neues Spiel zu starten.
     *
     * @author M. Paul
     */
    public void navigateBack(View view) {
        this.finish();
    }
}