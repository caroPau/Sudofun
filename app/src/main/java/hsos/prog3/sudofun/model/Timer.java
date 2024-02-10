package hsos.prog3.sudofun.model;

import android.os.Handler;

public class Timer {


    private final Handler handler;    // regelt die Aktualisierung der UI im Hauptthread
    private final Runnable runnable;  // zur periodischen Aktualisierung des Timers
    private long start;         // Zeitstempel zum Startzeitpunkt
    private boolean isRunning;  // Zeigt an ob der Timer läuft
    private long millisSinceStart; //Verstrichene Millisekunden seit Start
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
                millisSinceStart = System.currentTimeMillis() - start;
                seconds = (int)millisSinceStart / 1000;
                minutes = seconds / 60;

                if (isRunning) {
                    handler.postDelayed(this, 1000);
                }
            }
        };
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public Handler getHandler() {
        return handler;
    }

    public long getMillisSinceStart() {
        return millisSinceStart;
    }

    public void setMillisSinceStart(long millisSinceStart) {
        this.millisSinceStart = millisSinceStart;
    }
}
