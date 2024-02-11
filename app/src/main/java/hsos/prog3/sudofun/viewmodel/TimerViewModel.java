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

    /**
     * Konstruktor
     */
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
        timer.getHandler().post(timer);
    }

    /**
     * Pausiert den Timer
     */
    public void pause(){
        timer.setRunning(false);
        timer.getHandler().removeCallbacks(timer);
    }

    /**
     * Startet den Timer nach dem Pausieren
     */
    public void startAfterPause(){
        timer.setRunning(true);
        timer.getHandler().post(timer);
    }

    /**
     * Rechnet die verstrichene Zeit (in Millisekunden) in Minuten und Sekunden um und speichert diese in den entsprechenden Variablen des Timers
     */
    public void millisToSecondsAndMinutes(){
        int secondsTemp = ((int) (timer.getMillisSinceStart() / 1000));
        timer.setMinutes(secondsTemp / 60);
        timer.setSeconds(secondsTemp - timer.getMinutes() * 60);
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

    /**
     *  Getter
     */
    public Timer getTimer() {
        return timer;
    }

}
