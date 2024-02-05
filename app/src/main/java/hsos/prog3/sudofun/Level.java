package hsos.prog3.sudofun;

public enum Level {
    EASY(35),
    MEDIUM(30),
    HARD(25);

    final int openCells;

    Level(int openCells) {
        this.openCells = openCells;
    }
}
