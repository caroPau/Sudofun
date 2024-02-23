package hsos.prog3.sudofun.model;

import android.widget.EditText;
import android.widget.GridLayout;

import java.util.ArrayList;

import hsos.prog3.sudofun.viewmodel.TimerViewModel;

/**
 * Klasse Play hält alle Parameter eines laufenden Spiels.
 *
 * @author C.Paul
 */
public class Play {
    private LevelEnum levelEnum;
    private UserEntity user;
    private TimerViewModel timer;
    private ArrayList<Integer> occupiedCells;
    private int[][] field;
    private int[][] solvedField;
    private final boolean[][] freeCellsArray;
    private boolean noteMode;
    private int rowHint;
    private int columnHint;
    private int freeCells;
    private EditText lastFocusedCell;
    private GridLayout lastFocusedGrid;
    private int playedGames;

    //  Konstruktor
    public Play() {
        occupiedCells = new ArrayList<>();
        freeCellsArray = new boolean[9][9];
        playedGames = 0;
    }

    //Getter

    public UserEntity getUser() {
        return user;
    }

    public boolean isNoteMode() {
        return noteMode;
    }

    public LevelEnum getLevel() {
        return levelEnum;
    }

    public int[][] getField() {
        return this.field;
    }

    public TimerViewModel getTimer() {
        return timer;
    }

    public int[][] getSolvedField() {
        return solvedField;
    }

    public boolean[][] getFreeCellsArray() {
        return freeCellsArray;
    }

    public ArrayList<Integer> getOccupiedCells() {
        return occupiedCells;
    }

    public int getRowHint() {
        return rowHint;
    }

    public int getColumnHint() {
        return columnHint;
    }

    public int getFreeCells() {
        return freeCells;
    }

    public EditText getLastFocusedCell() {
        return lastFocusedCell;
    }

    public GridLayout getLastFocusedGrid() {
        return lastFocusedGrid;
    }

    //  Setter

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setNoteMode(boolean noteMode) {
        this.noteMode = noteMode;
    }


    public void setLevel(LevelEnum levelEnum) {
        this.levelEnum = levelEnum;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public void setTimer(TimerViewModel timer) {
        this.timer = timer;
    }

    public void setSolvedField(int[][] solvedField) {
        this.solvedField = solvedField;
    }

    public void setOccupiedCells(ArrayList<Integer> occupiedCells) {
        this.occupiedCells = occupiedCells;
    }

    public void setRowHint(int rowHint) {
        this.rowHint = rowHint;
    }

    public void setColumnHint(int columnHint) {
        this.columnHint = columnHint;
    }

    public void setFreeCells(int freeCells) {
        this.freeCells = freeCells;
    }

    public void setLastFocusedCell(EditText lastFocusedCell) {
        this.lastFocusedCell = lastFocusedCell;
    }

    public void setLastFocusedGrid(GridLayout lastFocusedGrid) {
        this.lastFocusedGrid = lastFocusedGrid;
    }

    public int getPlayedGames() {
        return playedGames;
    }
}
