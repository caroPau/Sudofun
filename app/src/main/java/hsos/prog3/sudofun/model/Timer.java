package hsos.prog3.sudofun.model;

import android.os.Handler;

public class Timer implements Runnable {
    private final Handler handler;    // regelt die Aktualisierung der UI im Hauptthread
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
    }

    /**
     *  Getter
     */
    public int getMinutes() {
        return minutes;
    }
    public int getSeconds() {
        return seconds;
    }
    public Handler getHandler() {
        return handler;
    }
    public long getMillisSinceStart() {
        return millisSinceStart;
    }
    public boolean isRunning() {
        return isRunning;
    }

    /**
     *  Setter
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    public void setStart(long start) {
        this.start = start;
    }
    public void setRunning(boolean running) {
        isRunning = running;
    }
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }


    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        millisSinceStart = System.currentTimeMillis() - start;
        if (isRunning) {
            handler.postDelayed(this, 1000);
        }
    }
}
