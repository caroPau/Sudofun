package hsos.prog3.sudofun.viewmodel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import hsos.prog3.sudofun.model.Play;

/**
 * Hilfsklasse zum Erstellen und Lösen von Sudokus
 * Author: C. Paul
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
    private boolean isDigitInRow(int row, int digit, int[][] field) {
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
    private boolean isDigitInColumn(int column, int digit, int[][] field) {
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
    private boolean isDigitInBlock(int row, int column, int digit, int[][] field) {
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
    public boolean isValid(int row, int column, int digit, int[][] field) {
        if (!isDigitInRow(row, digit, field) && !isDigitInColumn(column, digit, field) && !isDigitInBlock(row, column, digit, field)) {
            return true;
        }
        return false;
    }

    /**
     * Füllt ein zufällig ausgewähltes leeres Feld in einem übergebenen Sudoku-Feld
     * @param field Das Sudoku-Feld als 2D-Array
     * @param solvedField Die Lösung des Sudoku-Feldes
     */
    public void getRandomFreeCell(int[][] field, int[][] solvedField){
        Random rand = new Random();
        int row;
        int column;

        while(true){
            row = rand.nextInt(9);
            column = rand.nextInt(9);
            if(field[row][column] == 0){
                field[row][column] = solvedField[row][column];
                return;
            }
        }
    }
    private int coordinateAsOneNumber(int row, int column){
        return row * 10 + column;
    }

    public boolean isCoordinateEditable(ArrayList<Integer> occupiedCells, int row, int column){
        return !occupiedCells.contains(coordinateAsOneNumber(row, column));
    }

    public ArrayList<Integer> getOccupiedCells(int[][] field){
        ArrayList<Integer> occupiedCells = new ArrayList<>();
        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field.length; j++){
                if(field[i][j] != 0){
                    occupiedCells.add(coordinateAsOneNumber(i, j));
                }
            }
        }
        return occupiedCells;
    }

    /*public Map<Integer, Integer> getOccupiedCells(int[][] field){
        Map<Integer, Integer> occupiedCells = null;
        for(int i = 0; i <= 8; i++){
            for(int j = 0; j <= 8; j++){
                if(field[i][j] != 0){
                    occupiedCells.put(i, j);
                }
            }
        }
        return occupiedCells;
    }*/
}
