package hsos.prog3.sudofun.viewmodel;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import hsos.prog3.sudofun.model.Timer;

/**
 *  Implementiert einen Timer und zeigt die verstrichene Zeit in einer TextView
 */
public class TimerViewModel{
    private Handler handler;

    private Timer timer;

    public TimerViewModel(){
        handler = new Handler(Looper.myLooper());
        timer = new Timer(handler);
    }

    /**
     * Startet den Timer
     */
    public void start(){
        timer.setStart(System.currentTimeMillis());
        timer.setRunning(true);
        timer.getHandler().post(timer.getRunnable());
    }

    /**
     * Pausiert den Timer
     */
    public void pause(){
        timer.setRunning(false);
        timer.getHandler().removeCallbacks(timer.getRunnable());
    }

    /**
     * Startet den Timer nach dem Pausieren
     */
    public void startAfterPause(){
        timer.setRunning(true);
        timer.getHandler().post(timer.getRunnable());
    }

    /**
     *  Formatiert Timer zu einem String
     *
     */
    @NonNull
    @Override
    public String toString(){
        return timer.getMinutes() + ":" + timer.getSeconds();
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
