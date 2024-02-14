package hsos.prog3.sudofun.model;

public enum Level {
    EASY(40),
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
