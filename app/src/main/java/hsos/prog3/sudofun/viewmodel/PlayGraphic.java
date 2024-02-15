package hsos.prog3.sudofun.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;

import hsos.prog3.sudofun.viewmodel.PlayActivity;

import androidx.appcompat.content.res.AppCompatResources;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Play;


public class PlayGraphic {

    static PlayActivity playActivity;
    public static int getBildschirmBreite() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getBildschirmHoehe() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public PlayGraphic(PlayActivity playActivity){
        this.playActivity = playActivity;
    }

    //TODO: Feld dynamisch erzeugen, OnClickListener
    public void generateGrid(Context context, Play game, GridLayout grid){

        for(int row = 0; row <= 8; row++){
            for(int column = 0; column <= 8; column++){
                EditText editText = new EditText(context);
                editText.setId(game.getHelper().coordinateAsOneNumber(row, column));
                editText.setGravity(Gravity.CENTER);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setTextColor(Color.BLACK);
                editText.setBackground(AppCompatResources.getDrawable(context, R.drawable.edit_text_field_border_black));
                ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(getBildschirmBreite()/10,getBildschirmBreite()/10);
                editText.setLayoutParams(lparams);
                editText.setCursorVisible(false);
                if(game.getField()[row][column] != 0){
                    editText.setText(String.valueOf(game.getField()[row][column]));
                    editText.setEnabled(false);
                }
                TextWatcher textWatcher = setTextWatcher(row, column, game, editText);
                editText.addTextChangedListener(textWatcher);
                grid.addView(editText);
            }
        }

        View horLine1 = new View(context);
        ViewGroup.LayoutParams horLine1params = new ViewGroup.LayoutParams((int)(getBildschirmBreite()*0.8),(int)(getBildschirmBreite()*0.02));
        horLine1.setBackground(AppCompatResources.getDrawable(context, R.drawable.edit_text_field_border_black));
        horLine1.setLayoutParams(horLine1params);
        horLine1.setX(grid.getX());
        horLine1.setY(grid.getY());
        View horLine2 = new View(context);
        View vertLine1 = new View(context);
        View vertLine2 = new View(context);
    }

    public TextWatcher setTextWatcher(int row, int column, Play game, EditText editText){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null) {
                    if(game.getField()[row][column] == 0 && game.getHelper().isValid(row,column,Integer.parseInt(s.toString()),game.getField())){
                        game.setFreeCells(game.getFreeCells() - 1);
                        if(!s.toString().equals("")) {
                            game.getField()[row][column] = Integer.parseInt(s.toString());
                        }
                    } else {
                        System.out.println(editText.getText());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(game.getFreeCells() == 0){
                    playActivity.endGame();
                }
            }
        };
    }
}
