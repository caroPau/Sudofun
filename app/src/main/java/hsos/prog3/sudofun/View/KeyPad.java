package hsos.prog3.sudofun.View;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

    private View getNote(int index,GridLayout noteGrid){
        return noteGrid.getChildAt(index-1);
    }
    public View.OnClickListener onClickListener(Button button,PlayGraphic graphic, Play game){
        return view -> {
            CharSequence buttonText = button.getText();
            EditText focusedEditText = graphic.getFocusedEditText();
            GridLayout focusedNoteGrid = graphic.getFocusedNoteGrid();
            View note = null;
            if(game.isNoteMode()){
                switch(buttonText.toString()){
                    case "1":
                        note = getNote(1,focusedNoteGrid);
                        break;
                    case "2":
                        note = getNote(2,focusedNoteGrid);
                        break;
                    case "3":
                        note = getNote(3,focusedNoteGrid);
                        break;
                    case "4":
                        note = getNote(4,focusedNoteGrid);
                        break;
                    case "5":
                        note = getNote(5,focusedNoteGrid);
                        break;
                    case "6":
                        note = getNote(6,focusedNoteGrid);
                        break;
                    case "7":
                        note = getNote(7,focusedNoteGrid);
                        break;
                    case "8":
                        note = getNote(8,focusedNoteGrid);
                        break;
                    case "9":
                        note = getNote(9,focusedNoteGrid);
                        break;
                    case "clear":
                        for(int i = 1; i<=9;i++){
                            note = getNote(i,focusedNoteGrid);
                            note.setVisibility(View.INVISIBLE);
                        }
                        return;
                }
                if(note != null) {
                    if (note.getVisibility() == View.INVISIBLE) {
                        note.setVisibility(View.VISIBLE);
                    } else {
                        note.setVisibility(View.INVISIBLE);
                    }
                }
            }else{
                if(!buttonText.toString().equals("clear")) {
                    focusedEditText.setText(buttonText);
                }
                else{
                    focusedEditText.setText("");
                }
            }
        };
    }
}
