package hsos.prog3.sudofun.model;

/**
 * Enum für Schwierigkeitsgrade des Spiels.
 */
public enum Level {
    EASY(45),
    MEDIUM(35),
    HARD(28);

    private final int openCells;

    Level(int openCells) {
        this.openCells = openCells;
    }

    public int getOpenCells() {
        return openCells;
    }


}
