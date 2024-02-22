package hsos.prog3.sudofun.model;

import android.widget.EditText;
import android.widget.GridLayout;

import java.util.ArrayList;

import hsos.prog3.sudofun.viewmodel.TimerViewModel;

/**
 * Klasse Play hält alle Parameter eines laufenden Spiels.
 */
public class Play {
    private Level level;
    private UserEntity user;
    private TimerViewModel timer;
    private ArrayList<Integer> occupiedCells;
    private int[][] field;
    private int[][] solvedField;
    private boolean[][] freeCellsArray;
    private boolean noteMode;
    private int rowHint;
    private int columnHint;
    private int freeCells;
    private EditText lastFocusedCell;
    private GridLayout lastFocusedGrid;

    public Play() {
        occupiedCells = new ArrayList<>();
        freeCellsArray = new boolean[9][9];
    }

    public UserEntity getUser() {
        return user;
    }

    public boolean isNoteMode() {
        return noteMode;
    }

    public Level getLevel() {
        return level;
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

    public boolean[][] getFreeCellsArray(){
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

    public EditText getLastFocusedCell(){
        return lastFocusedCell;
    }

    public GridLayout getLastFocusedGrid(){
        return lastFocusedGrid;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setNoteMode(boolean noteMode) {
        this.noteMode = noteMode;
    }

    public void setLevel(Level level) {
        this.level = level;
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

    public void setOpenCells(int openCells){
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

    public void setLastFocusedCell(EditText lastFocusedCell){
        this.lastFocusedCell = lastFocusedCell;
    }

    public void setLastFocusedGrid(GridLayout lastFocusedGrid) {
        this.lastFocusedGrid = lastFocusedGrid;
    }
}
