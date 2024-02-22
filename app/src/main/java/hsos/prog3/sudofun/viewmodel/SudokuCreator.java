package hsos.prog3.sudofun.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hsos.prog3.sudofun.model.Level;

/**
 * Implementiert die Spiellogik.
 *
 * @author Carolin Paul
 */
public class SudokuCreator implements Runnable {
    /**
     * Verschiedene Schwierigkeitsstufen und die jeweils offengelegten Zellen
     */

    private int[][] solvedField;
    private Level level;
    private SudokuHelper helper;

    /**
     * Konstruktor
     *
     * @param level Schwierigkeitsgrad des Sudokus
     */
    public SudokuCreator(Level level, SudokuHelper helper){
        solvedField = new int[9][9];
        this.level = level;
        this.helper = helper;
    }



    /**
     * Füllt die Zellen eines 3x3-Blocks im Sudoku-Feld mit Ziffern, indem Ziffern von 1 bis 9 zufällig platziert werden.
     * Die Platzierung erfolgt unter Berücksichtigung der Sudoku-Regeln, um sicherzustellen, dass die generierte
     * Konfiguration gültig ist.
     *
     * @param field         Das Sudoku-Feld als 2D-Array.
     * @param xPosFirstCell Die x-Position der oberen linken Ecke des 3x3-Blocks.
     * @param yPosFirstCell Die y-Position der oberen linken Ecke des 3x3-Blocks.
     * @param rand          Ein Random-Objekt für die Zufallsgenerierung.
     */
    private void fillOneBox(int[][] field, int xPosFirstCell, int yPosFirstCell, Random rand) {
        List<Integer> list = new ArrayList<>();
        for (int k = 1; k <= 9; k++) {
            list.add(k);
        }
        for (int i = xPosFirstCell; i <= xPosFirstCell + 2; i++) {
            for (int j = yPosFirstCell; j <= yPosFirstCell + 2; j++) {
                int index = rand.nextInt(list.size());
                int digit = list.get(index);
                list.remove(index);
                if (helper.isValid(i, j, digit, field)) {
                    field[i][j] = digit;
                }
            }
        }
    }

    /**
     * Füllt die diagonalen 3x3-Blöcke im Sudoku-Feld mit Ziffern, indem für jede diagonale Box
     * die Methode {@code fillOneBox} aufgerufen wird.
     *
     * @param field Das Sudoku-Feld als 2D-Array.
     */
    private void fillDiagonalBoxes(int[][] field) {
        for (int i = 0; i <= 6; i += 3) {
            fillOneBox(field, i, i, new Random());
        }
    }

    /**
     * Löst das Sudoku-Feld rekursiv mit dem Backtracking-Algorithmus.
     *
     * @param field Das Sudoku-Feld als 2D-Array.
     * @return true, wenn eine Lösung gefunden wurde, andernfalls false.
     */
    private boolean solveSudoku(int[][] field) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (field[row][column] == 0) {
                    for (int digit = 1; digit <= 9; digit++) {
                        if (helper.isValid(row, column, digit, field)) {
                            field[row][column] = digit;
                            if (solveSudoku(field)) {
                                return true;
                            } else {
                                field[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Füllt eine bestimmte Zelle eines übergebenen Sudoku-Feldes mit der passenden Ziffer, wenn dies eindeutig ist
     *
     * @param field Sudoku-Feld als 2D-Array
     * @param row Reihennummer der Zelle
     * @param column Zeilennummer der Zelle
     */
    private void fillEasyCells(int[][] field, int row, int column){
        int counter = 0;
        int numberInQuestion = 0;
        for(int i = 1; i <= 9; i++){
            if(helper.isValid(row, column, i, field)){
                counter++;
                numberInQuestion = i;
            }
        }
        if(counter == 1){
            field[row][column] = numberInQuestion;
        }
    }

    /**
     * Prüft ob ein übergebenes Sudoku-Feld lösbar ist, durch das Ausfüllen eindeutiger Felder
     * @param field Das Sudoku-Feld als 2D-Array
     * @return true, wenn das Sudoku ohne Raten lösbar ist, andernfalls false
     */
    private boolean solveEasySudoku(int[][] field){
        int[][] tempSudoku = copySudoku(field);
        int counter = 0;
        int lastCounter = 0;
        while(true) {
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    if (tempSudoku[row][column] == 0) {
                        counter++;
                        fillEasyCells(tempSudoku, row, column);
                    }
                }
            }
            if(counter == lastCounter && counter > 0){
                return false;
            }
            if(counter == 0){
                return true;
            }
            lastCounter = counter;
            counter = 0;
        }
    }

    /**
     * Generiert ein lösbares Sudoku-Feld
     *
     * @return Ein lösbares Sudoku-Feld als 2D-Array.
     */
    private int[][] generateSolvableField() {
        int[][] field = new int[9][9];

        fillDiagonalBoxes(field);
        if (!solveSudoku(field)) {
          generateSolvableField();
        }
        this.solvedField = copySudoku(field);
        return field;
    }

    /**
     * Generiert ein spielbares Sudoku-Feld basierend auf dem angegebenen Schwierigkeitsgrad.
     *
     * @param lvl Der Schwierigkeitsgrad des zu generierenden Sudoku-Felds.
     * @return Ein spielbares Sudoku-Feld als 2D-Array.
     */
    private int[][] generatePlayableField(Level lvl) {
        int[][] field = generateSolvableField();
        this.solvedField = copySudoku(field);
        Random rand = new Random();
        int freeCells = 81 - lvl.getOpenCells();
        for (int i = freeCells; i != 0; i--){
            int randomRow = rand.nextInt(9);
            int randomColumn = rand.nextInt(9);
            if (field[randomRow][randomColumn] == 0) {
                i++;
            } else {
                field[randomRow][randomColumn] = 0;
            }
        }
        return field;
    }

    /* Zu Debugging-Zwecken */
    private static void printSudoku(int[][] field) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(field[i][j] + " ");
                if ((j + 1) % 3 == 0 && j < 8) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((i + 1) % 3 == 0 && i < 8) {
                System.out.println("------+-------+------");
            }
        }
    }

    /**
     * Erstellt eine tiefe Kopie eines übergebenen 9x9-Arrays
     * @param original Das zu kopierende Sudokufeld als 2D-Array
     * @return Eine Kopie von @original
     */
    private static int[][] copySudoku(int[][] original){
        if(original == null){
            return null;
        }
        int[][] copy = new int[9][9];

        for(int i = 0; i <= 8; i++){
            System.arraycopy(original[i], 0, copy[i], 0, 9);
        }
        return copy;
    }

    /**
     * Generiert ein Sudoku basierend auf dem gewünschten Level
     * @param lvl Der Schwierigkeitsgrad des Sudokus
     * @return Ein spielbares Sudoku-Feld als 2D-Array
     */
    public int[][] createSudoku(Level lvl){
        int[][] sudoku = generatePlayableField(lvl);
        if(lvl == Level.EASY){
            while(!solveEasySudoku(sudoku)){
                sudoku = generatePlayableField(lvl);
            }
        }
        return sudoku;
    }

    public int[][] getSolvedField(){
        return solvedField;
    }

    public void setSolvedField(int[][] solvedField) {
        this.solvedField = solvedField;
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
    }
}

