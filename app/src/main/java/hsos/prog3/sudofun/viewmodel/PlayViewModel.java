package hsos.prog3.sudofun.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import hsos.prog3.sudofun.model.Play;

public class PlayViewModel extends AndroidViewModel {


    public PlayViewModel(@NonNull Application application) {
        super(application);

    }

    public void reactToNewNumber(Play game, int row, int column, int number){
        if (game.getField()[row][column] == 0 && game.getHelper().isValid(row, column, number, game.getField())) {
            game.setFreeCells(game.getFreeCells() - 1);
            game.getField()[row][column] = number;
        }
    }
}
