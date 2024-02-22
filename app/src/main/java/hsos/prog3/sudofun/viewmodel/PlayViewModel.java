package hsos.prog3.sudofun.viewmodel;

import android.app.Application;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;

import hsos.prog3.sudofun.View.PlayActivity;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.Play;
import hsos.prog3.sudofun.model.UserEntity;

public class PlayViewModel extends AndroidViewModel {

    private final Play play;
    private final SudokuHelper helper;

    public PlayViewModel(@NonNull Application application) {
        super(application);
        play = new Play();
        helper = new SudokuHelper();
    }

    public void setUser(UserEntity user){
        play.setUser(user);
    }
    public Level getLevel(){
        return play.getLevel();
    }
    public int getCoordinateRow(){
        return play.getRowHint();
    }
    public int getCoordinateColumn(){
        return play.getColumnHint();
    }

    public void setCoordinateRow(int row){
        play.setRowHint(row);
    }

    public void setCoordinateColumn(int column){
        play.setColumnHint(column);
    }
    public void setLastFocusedCell(EditText lastFocusedCell){
        play.setLastFocusedCell(lastFocusedCell);
    }

    public void setLastFocusedGrid(GridLayout grid){
        play.setLastFocusedGrid(grid);
    }
    public void setLevel(Level level){
        play.setLevel(level);
    }
    public void setField(int[][] field){
        play.setField(field);
    }
    public GridLayout getLastFocusedGrid(){
        return play.getLastFocusedGrid();
    }
    public EditText getLastFocusedCell(){
        return play.getLastFocusedCell();
    }
    public UserEntity getUser(){
        return play.getUser();
    }
    public SudokuHelper getHelper(){
        return helper;
    }


    public int[][] getField(){
        return play.getField();
    }

    public boolean reactToNewNumber(int row, int column, int number) {
        if(getField()[row][column] == 0) {
            if (getHelper().isValid(row, column, number, getField())) {
                setFreeCells(getFreeCells() - 1);
                getField()[row][column] = number;
                return true;
            }else{
                return false;
            }
        }
        else {
            if(number == getField()[row][column]){
                return true;
            }
            if (getHelper().isValid(row, column, number, getField())) {
                setFreeCells(getFreeCells() - 1);
                getField()[row][column] = number;
                return true;
            } else if (getHelper().isValid(row, column, getField()[row][column], getField())) {
                    getField()[row][column] = 0;
                    setFreeCells(getFreeCells() + 1);
                    return false;
            }
            else {
                getField()[row][column] = 0;
                return false;
            }
        }
    }

    public void reactToClear(){
        if(getField()[getCoordinateRow()][getCoordinateColumn()] != 0){
            getField()[getCoordinateRow()][getCoordinateColumn()] = 0;
            setFreeCells(getFreeCells() + 1);
        }
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

    public TimerViewModel getTimer() {
        return play.getTimer();
    }

    public void setOccupiedCells(ArrayList<Integer> occupiedCells) {
        play.setOccupiedCells(occupiedCells);
    }

    public void setOpenCells(int openCells) {
        play.setOpenCells(openCells);
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
    public ArrayList<Integer> getOccupiedCells(){
        return play.getOccupiedCells();
    }

    public void setNoteMode(boolean noteMode) {
        play.setNoteMode(noteMode);
    }

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

    public Level getSelectedLevel(int lvl, PlayActivity playActivity) {
        switch (lvl) {
            case 0:
                setLevel(Level.EASY);
                break;
            case 1:
                setLevel(Level.MEDIUM);
                break;
            case 2:
                setLevel(Level.HARD);
                break;
            default:
                playActivity.startLevelActivity();
                break;
        }
        return getLevel();
    }

    public long getBestTime(){
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

    public void updateUser(){
        switch (getLevel()){
            case EASY:
                if(getUser().highscoreEasy == 0 || getTimer().getMillisSinceStart() < getUser().highscoreEasy) {
                    getUser().highscoreEasy = getTimer().getMillisSinceStart();
                }
                getUser().gamesEasy++;
                break;
            case MEDIUM:
                if(getUser().highscoreMedium == 0 || getTimer().getMillisSinceStart() < getUser().highscoreMedium) {
                    getUser().highscoreMedium = getTimer().getMillisSinceStart();
                }
                getUser().gamesMedium++;
                break;
            case HARD:
                if(getUser().highscoreHard == 0 || getTimer().getMillisSinceStart() < getUser().highscoreHard) {
                    getUser().highscoreHard = getTimer().getMillisSinceStart();
                }
                getUser().gamesHard++;
                break;
            default:
                break;
        }
    }
}
