package hsos.prog3.sudofun.viewmodel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import hsos.prog3.sudofun.R;


public class PlayGraphic {

    //TODO: Feld dynamisch erzeugen, OnClickListener
    public void generateGrid(Context context, int[][] field, GridLayout grid){
        for(int i = 0; i <= 8; i++){
            for(int j = 0; j <= 8; j++){
                EditText editText = new EditText(context);
//                editText.setLayoutParams(grid.LayoutParams(GridLayout.spec(i), GridLayout.spec(j)));
                editText.setGravity(Gravity.CENTER);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setTextColor(Color.BLACK);
                editText.setBackground(AppCompatResources.getDrawable(context, R.drawable.blackbordershape));
                ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(42,42);
                editText.setLayoutParams(lparams);
                /*android:id="@+id/TxtR1C1"
                android:layout_width="42sp"
                android:layout_height="42sp"
                android:background="@drawable/blackbordershape"
                android:digits="123456789"
                android:ems="10"
                android:gravity="center"
                android:inputType="number"
                android:maxLength="1" />*/

                if(field[i][j] != 0){
                    editText.setText(String.valueOf(field[i][j]));
                    editText.setEnabled(false);
                }
                grid.addView(editText);
            }
        }
    }
}
