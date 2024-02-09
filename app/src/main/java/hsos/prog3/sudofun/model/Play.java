package hsos.prog3.sudofun.model;

import hsos.prog3.sudofun.viewmodel.SudokuHelper;
import hsos.prog3.sudofun.viewmodel.TimerViewModel;

public class Play {
    private Level level;
    private int[][] field;

    private int[][] solvedField;
    private TimerViewModel timer;
    private SudokuHelper helper;

    public Play(){
        helper = new SudokuHelper();
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
}
