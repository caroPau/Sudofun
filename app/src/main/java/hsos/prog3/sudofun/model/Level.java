package hsos.prog3.sudofun.model;

public enum Level {
    EASY(35),
    MEDIUM(30),
    HARD(25);

    private final int openCells;

    Level(int openCells) {
        this.openCells = openCells;
    }

    public int getOpenCells() {
        return openCells;
    }
}
