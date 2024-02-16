package hsos.prog3.sudofun.model;

import java.util.ArrayList;

import hsos.prog3.sudofun.viewmodel.SudokuHelper;
import hsos.prog3.sudofun.viewmodel.TimerViewModel;

public class Play {
    private Level level;
    private int[][] field;

    public boolean isNoteMode() {
        return noteMode;
    }

    private boolean noteMode;

    public void setNoteMode(boolean noteMode) {
        this.noteMode = noteMode;
    }
    private int[][] solvedField;
    private TimerViewModel timer;
    private SudokuHelper helper;
    private boolean isFinished;
    private ArrayList<Integer> occupiedCells;
    private int openCells;
    private int rowHint;
    private int columnHint;
    private int freeCells;

    public Play(){
        helper = new SudokuHelper();
        occupiedCells = new ArrayList<>();
        isFinished = false;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int[][] getField() {
        return this.field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public TimerViewModel getTimer() {
        return timer;
    }

    public void setTimer(TimerViewModel timer) {
        this.timer = timer;
    }
    public int[][] getSolvedField() {
        return solvedField;
    }

    public void setSolvedField(int[][] solvedField) {
        this.solvedField = solvedField;
    }

    public SudokuHelper getHelper() {
        return helper;
    }

    public void setHelper(SudokuHelper helper) {
        this.helper = helper;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public ArrayList<Integer> getOccupiedCells() {
        return occupiedCells;
    }

    public void setOccupiedCells(ArrayList<Integer> occupiedCells) {
        this.occupiedCells = occupiedCells;
    }

    public int getOpenCells() {
        return openCells;
    }

    public void setOpenCells(int openCells) {
        this.openCells = openCells;
    }

    public int getRowHint() {
        return rowHint;
    }

    public void setRowHint(int rowHint) {
        this.rowHint = rowHint;
    }

    public int getColumnHint() {
        return columnHint;
    }

    public void setColumnHint(int columnHint) {
        this.columnHint = columnHint;
    }

    public int getFreeCells() {
        return freeCells;
    }

    public void setFreeCells(int freeCells) {
        this.freeCells = freeCells;
    }
}
