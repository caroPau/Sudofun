package hsos.prog3.sudofun.viewmodel;

import android.os.Handler;
import android.widget.TextView;

import java.util.Locale;


/**
 *  Implementiert einen Timer und zeigt die verstrichene Zeit in einer TextView.
 */
public class TimerViewModel{

    private Runnable timerRunnable;
    private final Handler handler;
    private long start;         // Zeitstempel zum Startzeitpunkt

    private long millisSinceStart; //Verstrichene Millisekunden seit Start

    private long pause;        //Zeitstempel zum Pausezeitpunkt

    private int seconds;        // Verstrichene Sekunden seit Start

    private int minutes;        // Verstrichene Minuten seit Start

    private boolean isRunning;  // Zeigt an ob der Timer läuft

    private TextView actualTimerView;

    /**
     * Konstruktor
     */
    public TimerViewModel(){
        handler = new Handler();
        isRunning = false;
    }

    /**
     * Startet den Timer
     */
    public void start(){
        isRunning = true;
        start = System.currentTimeMillis();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                millisSinceStart = System.currentTimeMillis() - start;
                actualTimerView.setText(stringify());
                millisToSecondsAndMinutes();
                if(isRunning) {
                    handler.postDelayed(this, 1000);
                }
            }

        };
    }

    /**
     * Pausiert den Timer
     */
    public void pause(){
        isRunning = false;
        pause = System.currentTimeMillis();

    }

    /**
     * Startet den Timer nach dem Pausieren
     */
    public void startAfterPause(){
        isRunning = true;
        start -= (pause-System.currentTimeMillis());
        handler.postDelayed(timerRunnable,1000);
    }

    /**
     * Rechnet die verstrichene Zeit (in Millisekunden) in Minuten und Sekunden um und speichert diese in den entsprechenden Variablen des Timers
     */
    public void millisToSecondsAndMinutes(){
        int secondsTemp = ((int) (millisSinceStart / 1000));
        minutes = secondsTemp / 60;
        seconds = secondsTemp - minutes * 60;
    }

    /**
     *  Formatiert Timer zu einem String
     *
     */
    public String stringify(){
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    /**
     *  Getter
     */

    public long getMillisSinceStart() {
        return millisSinceStart;
    }

    public Runnable getTimerRunnable() {
        return timerRunnable;
    }
    public boolean isRunning(){
        return this.isRunning;
    }

    /**
     *  Setter
     */

    public void setActualTimerView(TextView txtview){
        actualTimerView = txtview;
    }

    public void reset() {
        isRunning = false;
        handler.removeCallbacks(timerRunnable);
        start = 0;
        pause = 0;
        millisSinceStart = 0;
        seconds = 0;
        minutes = 0;
        actualTimerView = null;
    }
}
