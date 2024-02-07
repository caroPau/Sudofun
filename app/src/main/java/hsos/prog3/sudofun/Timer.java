package hsos.prog3.sudofun;

import android.os.Handler;

/**
 *  Implementiert einen Timer und zeigt die verstrichene Zeit in einer TextView
 */
public class Timer{
    private final Handler handler;    // regelt die Aktualisierung der UI im Hauptthread
    private final Runnable runnable;  // zur periodischen Aktualisierung des Timers
    private long start;         // Zeitstempel zum Startzeitpunkt
    private boolean isRunning;  // Zeigt an ob der Timer läuft
    private int seconds;        // Verstrichene Sekunden seit Start
    private int minutes;        // Verstrichene Minuten seit Start

    /**
     * Konstruktor
     *
     * @param handler Zur Aktualisierung der UI
     */
    public Timer(Handler handler) {
        this.handler = handler;
        runnable = new Runnable() {

            @Override
            public void run() {
                long millisSinceStart = System.currentTimeMillis() - start;
                seconds = (int)millisSinceStart / 1000;
                minutes = seconds / 60;

                if (isRunning) {
                    handler.postDelayed(this, 1000);
                }
            }
        };
    }

    /**
     * Startet den Timer
     */
    public void start(){
        start = System.currentTimeMillis();
        isRunning = true;
        handler.post(runnable);
    }

    /**
     * Pausiert den Timer
     */
    public void pause(){
        isRunning = false;
        handler.removeCallbacks(runnable);
    }

    /**
     * Startet den Timer nach dem Pausieren
     */
    public void startAfterPause(){
        isRunning = true;
        handler.post(runnable);
    }

    /**
     *  Formatiert Timer zu einem String
     *
     */
    @Override
    public String toString(){
        return minutes + ":" + seconds;
    }


}
