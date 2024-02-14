package hsos.prog3.sudofun.viewmodel;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.annotation.NonNull;

import hsos.prog3.sudofun.model.Timer;

/**
 *  Implementiert einen Timer und zeigt die verstrichene Zeit in einer TextView
 */
public class TimerViewModel{

    private Runnable timerRunnable;
    private Handler handler;
    private Timer timer;

    private TextView actualTimerView;

    private TextView oldTimerView;

    /**
     * Konstruktor
     */
    public TimerViewModel(){
        handler = new Handler();
        timer = new Timer(handler);
    }

    /**
     * Startet den Timer
     */
    public void start(){
        timer.setStart(System.currentTimeMillis());
        timer.setRunning(true);
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                actualTimerView.setText(stringify());
                millisToSecondsAndMinutes();
                timer.getHandler().postDelayed(this, 1000);
            }

        };
        timer.getHandler().postDelayed(timerRunnable,1000);
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
        timer.getHandler().postDelayed(timerRunnable,1000);
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
    //@NonNull
    //@Override
    public String stringify(){
        return timer.getMinutes() + ":" + timer.getSeconds();
    }

    /**
     *  Getter
     */
    public Timer getTimer() {
        return timer;
    }

    public void setActualTimerView(TextView txtview){
        actualTimerView = txtview;
    }

    public void setOldTimerView(TextView txtview){
        oldTimerView = txtview;
    }

}
