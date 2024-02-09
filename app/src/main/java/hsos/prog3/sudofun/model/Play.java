package hsos.prog3.sudofun.model;

import hsos.prog3.sudofun.viewmodel.TimerViewModel;

public class Play {
    private Level level;
    private int[][] field;
    private TimerViewModel timer;

    public Play(){
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
}
