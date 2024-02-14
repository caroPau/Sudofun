package hsos.prog3.sudofun.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.appcompat.content.res.AppCompatResources;

import hsos.prog3.sudofun.R;



public class PlayGraphic {

    public static int getBildschirmBreite() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getBildschirmHoehe() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    //TODO: Feld dynamisch erzeugen, OnClickListener
    public void generateGrid(Context context, int[][] field, GridLayout grid, SudokuHelper helper){
        for(int row = 0; row <= 8; row++){
            for(int column = 0; column <= 8; column++){
                EditText editText = new EditText(context);
                editText.setId(helper.coordinateAsOneNumber(row, column));
                editText.setGravity(Gravity.CENTER);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setTextColor(Color.BLACK);
                editText.setBackground(AppCompatResources.getDrawable(context, R.drawable.edit_text_field_border_black));
                ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(getBildschirmBreite()/10,getBildschirmBreite()/10);
                editText.setLayoutParams(lparams);

                if(field[row][column] != 0){
                    editText.setText(String.valueOf(field[row][column]));
                    editText.setEnabled(false);
                }


                int finalRow = row;
                int finalColumn = column;
                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s != null) {
                            field[finalRow][finalColumn] = Integer.parseInt(s.toString());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };
                editText.addTextChangedListener(textWatcher);
                grid.addView(editText);
            }
        }
    }
}
