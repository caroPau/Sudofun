package hsos.prog3.sudofun.viewmodel;

import android.app.Application;
import android.widget.EditText;

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

    public void setLastFocusedCell(EditText lastFocusedCell){
        play.setLastFocusedCell(lastFocusedCell);
    }

    public EditText getLastFocusedCell(){
        return play.getLastFocusedCell();
    }
    public void reactToNewNumber(Play game, int row, int column, int number){
        if (game.getField()[row][column] == 0 && game.getHelper().isValid(row, column, number, game.getField())) {
            game.setFreeCells(game.getFreeCells() - 1);
            game.getField()[row][column] = number;
        }
    }

    public void reactToClear(Play game){
        if(game.getField()[game.getRowHint()][game.getColumnHint()] != 0){
            game.getField()[game.getRowHint()][game.getColumnHint()] = 0;
            game.setFreeCells(game.getFreeCells() + 1);
        }
    }
}
