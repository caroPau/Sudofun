package hsos.prog3.sudofun.View;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import hsos.prog3.sudofun.model.Play;
import hsos.prog3.sudofun.viewmodel.PlayActivity;
import hsos.prog3.sudofun.viewmodel.PlayGraphic;

public class KeyPad {
    PlayActivity playActivity;
    Play game;

    PlayGraphic graphic;
    public KeyPad(PlayActivity playActivity, Play game, PlayGraphic graphic){
        this.playActivity = playActivity;
        this.game = game;
        this.graphic = graphic;
        registerListener(playActivity.getBinding().numbOne);
        registerListener(playActivity.getBinding().numbTwo);
        registerListener(playActivity.getBinding().numbThree);
        registerListener(playActivity.getBinding().numbFour);
        registerListener(playActivity.getBinding().numbFive);
        registerListener(playActivity.getBinding().numbSix);
        registerListener(playActivity.getBinding().numbSeven);
        registerListener(playActivity.getBinding().numbEight);
        registerListener(playActivity.getBinding().numbNine);
        registerListener(playActivity.getBinding().clearButton);

    }
    private void registerListener(Button button){
        View.OnClickListener numbClickListener =  onClickListener(button,graphic,game);
        button.setOnClickListener(numbClickListener);
    }
    public View.OnClickListener onClickListener(Button button,PlayGraphic graphic, Play game){
        return view -> {
            CharSequence buttonText = button.getText();
            EditText focusedEditText = graphic.getFocusedEditText();
            GridLayout focusedNoteGrid = graphic.getFocusedNoteGrid();
            if(game.isNoteMode()){
                //focusedNoteGrid
            }else{
                if(buttonText.toString()!="clear") {
                    focusedEditText.setText(buttonText);
                }
            }
        };
    }
}
