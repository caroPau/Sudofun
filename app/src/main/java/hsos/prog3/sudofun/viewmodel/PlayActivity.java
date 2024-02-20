package hsos.prog3.sudofun.viewmodel;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Objects;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.View.PlayGraphic;
import hsos.prog3.sudofun.database.UserEntity;
import hsos.prog3.sudofun.databinding.ActivityPlayBinding;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.Play;

public class PlayActivity extends AppCompatActivity {
    static Play game;
    UserEntity user;
    PlayViewModel playViewModel;

    static PlayGraphic graphic;

    public ActivityPlayBinding getBinding() {
        return binding;
    }

    private ActivityPlayBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        String username = getIntent().getStringExtra("username");
        int level = getIntent().getIntExtra("selectedLevel", 0);
        binding = ActivityPlayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.buttonHint.setOnClickListener(this::buttonHintClickEvent);
        binding.btnPause.setChecked(false);
        setContentView(view);
        game = new Play();
        game.dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        playViewModel = new ViewModelProvider(this).get(PlayViewModel.class);
        if((user = game.dataViewModel.findByName(username)) == null){
            user = new UserEntity(username, 0, 0, 0, 0, 0, 0);
        }

        initGame(level);

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
        SudokuCreator creator = new SudokuCreator(getSelectedLevel(game, level));
        Thread threadCreator = new Thread(creator, "CreatorThread");
        threadCreator.start();
        game.setField(creator.createSudoku(getSelectedLevel(game, level)));
        game.setFreeCells(81 - game.getLevel().getOpenCells());
        game.setSolvedField(creator.getSolvedField());
        game.setTimer(new TimerViewModel());
        game.getTimer().setActualTimerView(binding.textViewTimer);
        //showBestTime();
        if(game.getHelper().getOccupiedCells(game.getField()) != null) {
            game.setOccupiedCells(game.getHelper().getOccupiedCells(game.getField()));
            game.setOpenCells(81 - game.getOccupiedCells().size());
        }
        graphic = new PlayGraphic(this,this, game, playViewModel);
        graphic.generateGrid(binding.gridLayoutSudoku,binding.gridLayoutMask ,binding.playScreen);
        game.getTimer().start();
        Thread threadTimer = new Thread(game.getTimer().getTimerRunnable());
        threadTimer.start();
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
                bestTime = user.gamesEasy;
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

    private void updateUser(Level level){
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
        game.dataViewModel.updateUserDB(user);
        Bundle bundle = new Bundle();
        bundle.putString("username", Objects.requireNonNull(user.username));
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
            List<EditText> editTexts = graphic.getEditTexts();
            if (isChecked) {
                for (GridLayout grid : noteGrids) {
                    grid.setEnabled(true);
                    grid.setVisibility(View.VISIBLE);
                }
                for(EditText editText : editTexts){
                    editText.setEnabled(false);
                }
                game.setNoteMode(true);
            } else {
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