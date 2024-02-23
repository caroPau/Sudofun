package hsos.prog3.sudofun.model;

/**
 * Enum für Schwierigkeitsgrade des Spiels.
 *
 * @author C.Paul
 */
public enum LevelEnum {
    EASY(45),
    MEDIUM(35),
    HARD(28);

    private final int openCells;

    LevelEnum(int openCells) {
        this.openCells = openCells;
    }

    public int getOpenCells() {
        return openCells;
    }


}
