package hsos.prog3.sudofun.viewmodel;

/**
 * Hilfsklasse zum Erstellen und Lösen von Sudokus
 */
public class SudokuHelper {

    public SudokuHelper() {
    }
    /**
     * Überprüft, ob die angegebene Ziffer in der angegebenen Zeile des Sudoku-Felds vorhanden ist.
     *
     * @param row   Die Zeilennummer, in der die Ziffer überprüft werden soll.
     * @param digit Die zu überprüfende Ziffer.
     * @param field Das Sudoku-Feld als 2D-Array.
     * @return true, wenn die Ziffer in der Zeile vorhanden ist, andernfalls false.
     */
    private static boolean isDigitInRow(int row, int digit, int[][] field) {
        for (int i = 0; i <= 8; i++) {
            if (field[row][i] == digit) {
                return true;
            }
        }

        return false;
    }

    /**
     * Überprüft, ob die angegebene Ziffer in der angegebenen Spalte des Sudoku-Felds vorhanden ist.
     *
     * @param column Die Spaltennummer, in der die Ziffer überprüft werden soll.
     * @param digit  Die zu überprüfende Ziffer.
     * @param field  Das Sudoku-Feld als 2D-Array.
     * @return true, wenn die Ziffer in der Spalte vorhanden ist, andernfalls false.
     */
    private static boolean isDigitInColumn(int column, int digit, int[][] field) {
        for (int i = 0; i <= 8; i++) {
            if (field[i][column] == digit) {
                return true;
            }
        }
        return false;
    }

    /**
     * Überprüft, ob die angegebene Ziffer in dem 3x3-Block vorhanden ist, zu dem die angegebene
     * Zeile und Spalte im Sudoku-Feld gehören.
     *
     * @param row    Zeilennummer der Zelle
     * @param column Spaltennummer der Zelle
     * @param digit  Die zu überprüfende Ziffer
     * @param field  Das Sudokufeld als 2D-Array
     * @return true, falls die Ziffer im Block vorhanden ist, andernfalls false
     */
    private static boolean isDigitInBlock(int row, int column, int digit, int[][] field) {
        int blockRow = row - row % 3;
        int blockColumn = column - column % 3;

        for (int i = blockRow; i < blockRow + 3; i++) {
            for (int j = blockColumn; j < blockColumn + 3; j++) {
                if (field[i][j] == digit) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Überprüft, ob die angegebene Ziffer an der angegebenen Position im Sudoku-Feld gültig ist.
     * Eine Ziffer ist gültig, wenn sie weder in der Zeile, noch in der Spalte, noch im Block bereits vorkommt.
     *
     * @param row    Die Zeilennummer, in der die Ziffer überprüft werden soll.
     * @param column Die Spaltennummer, in der die Ziffer überprüft werden soll.
     * @param digit  Die zu überprüfende Ziffer.
     * @param field  Das Sudoku-Feld als 2D-Array.
     * @return true, wenn die Ziffer gültig ist, andernfalls false.
     */
    static boolean isValid(int row, int column, int digit, int[][] field) {
        if (!isDigitInRow(row, digit, field) && !isDigitInColumn(column, digit, field) && !isDigitInBlock(row, column, digit, field)) {
            return true;
        }
        return false;
    }
}
