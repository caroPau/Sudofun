package hsos.prog3.sudofun.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;

import hsos.prog3.sudofun.viewmodel.PlayActivity;

import androidx.appcompat.content.res.AppCompatResources;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Play;


public class PlayGraphic {
    PlayActivity activity = new PlayActivity();

    public static int getBildschirmBreite() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getBildschirmHoehe() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
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

                if(game.getField()[row][column] != 0){
                    editText.setText(String.valueOf(game.getField()[row][column]));
                    editText.setEnabled(false);
                }
                TextWatcher textWatcher = activity.setTextWatcher(row, column);
                editText.addTextChangedListener(textWatcher);
                grid.addView(editText);
            }
        }
    }
}
