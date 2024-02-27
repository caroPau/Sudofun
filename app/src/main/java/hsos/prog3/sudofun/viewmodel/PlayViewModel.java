package hsos.prog3.sudofun.viewmodel;

import android.app.Application;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;

import hsos.prog3.sudofun.view.PlayActivity;
import hsos.prog3.sudofun.model.LevelEnum;
import hsos.prog3.sudofun.model.Play;
import hsos.prog3.sudofun.model.UserEntity;

/**
 * ViewModel für die Spielansicht.
 */
public class PlayViewModel extends AndroidViewModel {

    private final Play play;
    private final SudokuHelper helper;


    /**
     * Konstruktor
     *
     * @param application Die Anwendung, auf die das Viewmodel zugreift
     */
    public PlayViewModel(@NonNull Application application) {
        super(application);
        play = new Play();
        helper = new SudokuHelper();
    }

    /**
     * Getter für die Membervariablen von Play
     */
    public int getPlayedGames() {
        return play.getPlayedGames();
    }

    public LevelEnum getLevel() {
        return play.getLevel();
    }

    public int getCoordinateRow() {
        return play.getRowHint();
    }

    public int getCoordinateColumn() {
        return play.getColumnHint();
    }

    public GridLayout getLastFocusedGrid() {
        return play.getLastFocusedGrid();
    }

    public EditText getLastFocusedCell() {
        return play.getLastFocusedCell();
    }

    public UserEntity getUser() {
        return play.getUser();
    }

    public SudokuHelper getHelper() {
        return helper;
    }

    public int[][] getField() {
        return play.getField();
    }

    public TimerViewModel getTimer() {
        return play.getTimer();
    }

    public boolean[][] getFreeCellsArray() {
        return play.getFreeCellsArray();
    }

    public boolean isNoteMode() {
        return play.isNoteMode();
    }

    public int getFreeCells() {
        return play.getFreeCells();
    }

    public int[][] getSolvedField() {
        return play.getSolvedField();
    }

    public ArrayList<Integer> getOccupiedCells() {
        return play.getOccupiedCells();
    }

    /**
     * Setter für die Membervariablen von Play
     */
    public void setUser(UserEntity user) {
        play.setUser(user);
    }

    public void setPlayedGames(int playedGames) {
        play.setPlayedGames(playedGames);
    }

    public void setCoordinateRow(int row) {
        play.setRowHint(row);
    }

    public void setCoordinateColumn(int column) {
        play.setColumnHint(column);
    }

    public void setLastFocusedCell(EditText lastFocusedCell) {
        play.setLastFocusedCell(lastFocusedCell);
    }

    public void setLastFocusedGrid(GridLayout grid) {
        play.setLastFocusedGrid(grid);
    }

    public void setLevel(LevelEnum levelEnum) {
        play.setLevel(levelEnum);
    }

    public void setField(int[][] field) {
        play.setField(field);
    }

    public void setFreeCells(int freeCells) {
        play.setFreeCells(freeCells);
    }

    public void setSolvedField(int[][] solvedField) {
        play.setSolvedField(solvedField);
    }

    public void setTimer(TimerViewModel timerViewModel) {
        play.setTimer(timerViewModel);
    }

    public void setOccupiedCells(ArrayList<Integer> occupiedCells) {
        play.setOccupiedCells(occupiedCells);
    }

    public void setNoteMode(boolean noteMode) {
        play.setNoteMode(noteMode);
    }

    /**
     * Reagiert auf eine neue eingegebene Zahl im Sudoku-Feld.
     *
     * @param row    Die Zeilenposition der eingegebenen Zahl
     * @param column Die Spaltenposition der eingegebenen Zahl
     * @param number Die eingegebene Zahl
     * @return True, wenn die Zahl an dieser Position gültig oder die gleiche Zahl schon eingetragen ist, false wenn nicht
     * @author C.Paul
     */
    public boolean reactToNewNumber(int row, int column, int number) {
        // Wenn das Feld im Sudoku noch leer ist
        if (getField()[row][column] == 0) {
            // ist die Zahl an der Stelle valide
            if (getHelper().isValid(row, column, number, getField())) {
                setFreeCells(getFreeCells() - 1);   // decrementieren der freien Zellen
                getField()[row][column] = number;   // die Zahl wird an der Stelle ins Sudoku-Array eingetragen
                return true;
            } else {
                return false;
            }
            // Wenn im Feld an dieser Stelle bereits eine Zahl eingetragen ist
        } else {
            // Wenn die eingetragene Zahl dieselbe Zahl
            if (number == getField()[row][column]) {
                return true;
            }
            // wenn es nicht die gleiche Zahl ist, aber sie an dieser Stelle valide ist
            if (getHelper().isValid(row, column, number, getField())) {
                setFreeCells(getFreeCells() - 1);
                getField()[row][column] = number;
                return true;
                // wenn die neue Zahl an dieser Stelle nicht valide ist (die vorherige zahl war valide)
            } else if (getHelper().isValid(row, column, getField()[row][column], getField())) {
                getField()[row][column] = 0;    // Setze das Feld zurück auf 0 (leer)
                setFreeCells(getFreeCells() + 1);   //inkrementiere die freien Zellen
                return false;
            } else {
                // Sowohl die vorherige Zahl als auch die eingegebene Zahl sind ungültig
                getField()[row][column] = 0;
                return false;
            }
        }
    }

    /**
     * Implementiert die Funktion des clear-button
     *
     * @author C.Paul
     */
    public void reactToClear() {
        if (getField()[getCoordinateRow()][getCoordinateColumn()] != 0) {
            getField()[getCoordinateRow()][getCoordinateColumn()] = 0;
            setFreeCells(getFreeCells() + 1);
        }
    }


    /**
     * Implementiert das Zurücksetzen des Spiels
     *
     * @author M.Paul
     */
    public void reset() {
        if (getTimer() != null) {
            getTimer().reset();
        }
        setField(null);
        setSolvedField(null);
        getOccupiedCells().clear();
        setNoteMode(false);
        setLastFocusedCell(null);
        setLastFocusedGrid(null);
    }

    /**
     * Setzt das gewünschte Level im Spiel und startet die LevelActivity
     *
     * @author M.Paul
     */
    public LevelEnum getSelectedLevel(int lvl, PlayActivity playActivity) {
        switch (lvl) {
            case 0:
                setLevel(LevelEnum.EASY);
                break;
            case 1:
                setLevel(LevelEnum.MEDIUM);
                break;
            case 2:
                setLevel(LevelEnum.HARD);
                break;
            default:
                playActivity.startLevelActivity();
                break;
        }
        return getLevel();
    }

    /**
     * Wählt die Bestzeit des Spielers im gewählten Schwierigkeitsgrad
     *
     * @return die jeweilige Bestzeit
     * @author M.Paul
     */
    public long getBestTime() {
        long bestTime;
        switch (getLevel()) {
            case EASY:
                bestTime = getUser().highscoreEasy;
                break;
            case MEDIUM:
                bestTime = getUser().highscoreMedium;
                break;
            case HARD:
                bestTime = getUser().highscoreHard;
                break;
            default:
                bestTime = 0;
                break;
        }
        return bestTime;
    }

    /**
     * Vergleicht die Erreichte Spielzeit mit dem Highscore und updatet die gespielten Spiele, sowie bei Bedarf den Highscore
     *
     * @author C.Paul
     */
    public void updateUser() {
        switch (getLevel()) {
            case EASY:
                if (getUser().highscoreEasy == 0 || getTimer().getMillisSinceStart() < getUser().highscoreEasy) {
                    getUser().highscoreEasy = getTimer().getMillisSinceStart();
                }
                getUser().gamesEasy++;
                break;
            case MEDIUM:
                if (getUser().highscoreMedium == 0 || getTimer().getMillisSinceStart() < getUser().highscoreMedium) {
                    getUser().highscoreMedium = getTimer().getMillisSinceStart();
                }
                getUser().gamesMedium++;
                break;
            case HARD:
                if (getUser().highscoreHard == 0 || getTimer().getMillisSinceStart() < getUser().highscoreHard) {
                    getUser().highscoreHard = getTimer().getMillisSinceStart();
                }
                getUser().gamesHard++;
                break;
            default:
                break;
        }
    }
}
