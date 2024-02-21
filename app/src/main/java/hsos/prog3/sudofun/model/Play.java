package hsos.prog3.sudofun.model;

import android.widget.EditText;

import java.util.ArrayList;

import hsos.prog3.sudofun.viewmodel.DataViewModel;
import hsos.prog3.sudofun.viewmodel.SudokuHelper;
import hsos.prog3.sudofun.viewmodel.TimerViewModel;

public class Play {
    private Level level;
    private TimerViewModel timer;
    private SudokuHelper helper;
    private ArrayList<Integer> occupiedCells;
    public DataViewModel dataViewModel;
    private int[][] field;
    private int[][] solvedField;
    private boolean noteMode;
    private int rowHint;
    private int columnHint;
    private int freeCells;
    private int openCells;
    private EditText lastFocusedCell;

    //  Konstruktor
    public Play() {
        helper = new SudokuHelper();
        occupiedCells = new ArrayList<>();
    }

    //Getter
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

    public SudokuHelper getHelper() {
        return helper;
    }

    public int getOpenCells(){
        return this.openCells;
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

    //  Setter


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
        this.openCells = openCells;
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
}
