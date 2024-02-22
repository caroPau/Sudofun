package hsos.prog3.sudofun.viewmodel;

import android.app.Application;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import hsos.prog3.sudofun.model.Login;
import hsos.prog3.sudofun.model.Play;

public class PlayViewModel extends AndroidViewModel {

    private Play play;

    public PlayViewModel(@NonNull Application application) {
        super(application);
        play = new Play();

    }

    public Play getPlay() {
        return play;
    }

    public int getCoordinateRow(){
        return play.getRowHint();
    }
    public int getCoordinateColumn(){
        return play.getColumnHint();
    }

    public void setCoordinateRow(int row){
        play.setRowHint(row);
    }

    public void setCoordinateColumn(int column){
        play.setColumnHint(column);
    }
    public void setLastFocusedCell(EditText lastFocusedCell){
        play.setLastFocusedCell(lastFocusedCell);
    }

    public void setLastFocusedGrid(GridLayout grid){
        play.setLastFocusedGrid(grid);
    }

    public GridLayout getLastFocusedGrid(){
        return play.getLastFocusedGrid();
    }
    public EditText getLastFocusedCell(){
        return play.getLastFocusedCell();
    }

    public SudokuHelper getHelper(){
        return play.getHelper();
    }


    public int[][] getField(){
        return play.getField();
    }


    public boolean reactToNewNumber(Play game, int row, int column, int number) {
        if(game.getField()[row][column] == 0) {
            if (getHelper().isValid(row, column, number, game.getField())) {
                game.setFreeCells(game.getFreeCells() - 1);
                game.getField()[row][column] = number;
                return true;
            }else{
                return false;
            }
        }
        else {
            if (getHelper().isValid(row, column, number, game.getField())) {
                game.setFreeCells(game.getFreeCells() - 1);
                game.getField()[row][column] = number;
                return true;
            } else if (getHelper().isValid(row, column, game.getField()[row][column], game.getField())) {
                game.getField()[row][column] = 0;
                game.setFreeCells(game.getFreeCells() + 1);
                return false;
            } else {
                game.getField()[row][column] = 0;
                return false;
            }
        }
    }

    public void reactToClear(Play game){
        if(game.getField()[game.getRowHint()][game.getColumnHint()] != 0){
            game.getField()[game.getRowHint()][game.getColumnHint()] = 0;
            game.setFreeCells(game.getFreeCells() + 1);
        }
    }
}
