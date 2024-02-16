package hsos.prog3.sudofun.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hsos.prog3.sudofun.viewmodel.PlayActivity;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.List;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Play;


public class PlayGraphic {

    static PlayActivity playActivity;
    static int[] gridBasePos;
    static int fieldEdgeSize;
    static Context context;
    static Drawable[][] noteBackgroundSelector;

    static EditText[][] editTexts;
    List<GridLayout> noteGrids;

    public List<GridLayout> getNoteGrids() {
        return noteGrids;
    }
    static int gridLineStrength;
    public static int getBildschirmBreite() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getBildschirmHoehe() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public PlayGraphic(PlayActivity playActivity, Context context){
        this.context = context;
        this.playActivity = playActivity;
        gridBasePos = new int[2];
        gridBasePos[0] = (int)(getBildschirmBreite()*0.05);
        gridBasePos[1] = (int)(getBildschirmHoehe()*0.156);
        fieldEdgeSize = (int)(getBildschirmBreite()/10);
        gridLineStrength = (int)(getBildschirmBreite()*0.01);
        noteBackgroundSelector = new Drawable[3][3];
        noteBackgroundSelector[0][0] = AppCompatResources.getDrawable(context, R.drawable.note_field_border_corner_top_left);
        noteBackgroundSelector[0][1] = AppCompatResources.getDrawable(context, R.drawable.note_field_border_top);
        noteBackgroundSelector[0][2] = AppCompatResources.getDrawable(context, R.drawable.note_field_border_corner_top_right);
        noteBackgroundSelector[1][0] = AppCompatResources.getDrawable(context, R.drawable.note_field_border_left);
        noteBackgroundSelector[1][2] = AppCompatResources.getDrawable(context, R.drawable.note_field_border_right);
        noteBackgroundSelector[2][0] = AppCompatResources.getDrawable(context, R.drawable.note_field_border_corner_bottom_left);
        noteBackgroundSelector[2][1] = AppCompatResources.getDrawable(context, R.drawable.note_field_border_bottom);
        noteBackgroundSelector[2][2] = AppCompatResources.getDrawable(context, R.drawable.note_field_border_corner_bottom_right);
        noteGrids = new ArrayList<GridLayout>();
        editTexts = new EditText[9][9];
    }

    //TODO: Feld dynamisch erzeugen, OnClickListener
    public void generateGrid(Play game, GridLayout grid, RelativeLayout playScreen){
        for(int row = 0; row <= 8; row++){
            for(int column = 0; column <= 8; column++){
                EditText editText = new EditText(context);
                editTexts[row][column] = editText;
                editText.setGravity(Gravity.CENTER);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setTextColor(Color.BLACK);
                editText.setBackground(AppCompatResources.getDrawable(context, R.drawable.edit_text_field_border_black));
                ViewGroup.LayoutParams editTextParams = new ViewGroup.LayoutParams(getBildschirmBreite()/10,getBildschirmBreite()/10);
                editText.setLayoutParams(editTextParams);
                editText.setCursorVisible(false);
                if(game.getField()[row][column] != 0){
                    editText.setText(String.valueOf(game.getField()[row][column]));
                    editText.setEnabled(false);
                } else {
                    GridLayout noteGrid = new GridLayout(context);
                    noteGrid.setX(gridBasePos[0]+column*fieldEdgeSize);
                    noteGrid.setY(gridBasePos[1]+row*fieldEdgeSize);
                    noteGrid.setColumnCount(3);
                    noteGrid.setRowCount(3);
                    noteGrid.setVisibility(View.INVISIBLE);
                    int noteNum = 1;
                    for(int noteRow = 0; noteRow <= 2; noteRow++){
                        for(int noteColumn = 0; noteColumn <= 2; noteColumn++) {
                            TextView note = new TextView(context);
                            ViewGroup.LayoutParams noteParams = new ViewGroup.LayoutParams(getBildschirmBreite()/30,getBildschirmBreite()/30);
                            note.setLayoutParams(noteParams);
                            note.setGravity(Gravity.CENTER);
                            note.setTextColor(Color.BLACK);
                            note.setTextSize((float)(fieldEdgeSize*0.1));
                            if(noteRow == 1 && noteColumn == 1) {
                                note.setBackground(AppCompatResources.getDrawable(context, R.drawable.whiteshape));
                            } else {
                                note.setBackground(noteBackgroundSelector[noteRow][noteColumn]);
                            }
                            note.setText(String.valueOf(noteNum));
                            noteNum++;
                            noteGrid.addView(note);
                        }
                    }

                    playScreen.addView(noteGrid);
                    noteGrids.add(noteGrid);
                }
                TextWatcher textWatcher = setTextWatcher(row, column, game, editText);
                View.OnClickListener editTextClickListener =  onClickListener(editText,game);
                editText.setOnClickListener(editTextClickListener);
                editText.addTextChangedListener(textWatcher);
                grid.addView(editText);
            }
        }

        //horizontal gridLine1
        generateGridLine(playScreen,9*fieldEdgeSize,gridLineStrength
                ,gridBasePos[0],gridBasePos[1]+3*fieldEdgeSize-gridLineStrength/2);
        //horizontal gridLine2
        generateGridLine(playScreen, 9*fieldEdgeSize,gridLineStrength
                ,gridBasePos[0],gridBasePos[1]+6*fieldEdgeSize-gridLineStrength/2);
        //vertical gridLine1
        generateGridLine(playScreen,gridLineStrength,9*fieldEdgeSize
                ,gridBasePos[0]+3*fieldEdgeSize-gridLineStrength/2,gridBasePos[1]);
        //vertical gridLine2
        generateGridLine(playScreen, gridLineStrength,9*fieldEdgeSize
                ,gridBasePos[0]+6*fieldEdgeSize-gridLineStrength/2,gridBasePos[1]);
    }

    private void generateGridLine(RelativeLayout playScreen, int width, int height, int x, int y){
        View gridLine = new View(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width,height);
        gridLine.setBackground(AppCompatResources.getDrawable(context, R.drawable.blackshape));
        gridLine.setLayoutParams(layoutParams);
        gridLine.setX(x);
        gridLine.setY(y);
        playScreen.addView(gridLine);
    }

    public View.OnClickListener onClickListener(EditText editText, Play game){
        return view -> {
            if(game.isNoteMode()){
                editText.setEnabled(false);
            }
            else {
                editText.setEnabled(true);
            }
        };
    }

    public TextWatcher setTextWatcher(int row, int column, Play game, EditText editText){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null && !s.toString().equals("")) {
                    if(!game.isNoteMode()) {
                        if (game.getField()[row][column] == 0 && game.getHelper().isValid(row, column, Integer.parseInt(s.toString()), game.getField())) {
                            game.setFreeCells(game.getFreeCells() - 1);
                            game.getField()[row][column] = Integer.parseInt(s.toString());
                        }
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
