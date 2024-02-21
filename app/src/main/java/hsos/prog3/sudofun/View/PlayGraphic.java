package hsos.prog3.sudofun.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.List;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Play;
import hsos.prog3.sudofun.viewmodel.PlayViewModel;


public class PlayGraphic {

    PlayActivity playActivity;
    static int[] gridBasePos;
    static int fieldEdgeSize;
    Context context;
    PlayViewModel playViewModel;
    Play game;
    static Drawable[][] noteBackgroundSelector;

    public List<EditText> getEditTexts() {
        return editTexts;
    }

    List<EditText> editTexts;

    List<GridLayout> noteGrids;

    public List<GridLayout> getNoteGrids() {
        return noteGrids;
    }
    static int gridLineStrength;

    public GridLayout getFocusedNoteGrid() {
        return focusedNoteGrid;
    }

    public void setFocusedNoteGrid(GridLayout focusedNoteGrid) {
        this.focusedNoteGrid = focusedNoteGrid;
    }

    GridLayout focusedNoteGrid;

    public EditText getFocusedEditText() {
        return focusedEditText;
    }

    public void setFocusedEditText(EditText focusedEditText) {
        this.focusedEditText = focusedEditText;
    }

    EditText focusedEditText;

    KeyPad keyPad;

    public static int getBildschirmBreite() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getBildschirmHoehe() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public PlayGraphic(PlayActivity playActivity, Context context, Play game, PlayViewModel playViewModel){
        this.context = context;
        this.playActivity = playActivity;
        this.game = game;
        this.playViewModel = playViewModel;
        gridBasePos = new int[2];
        gridBasePos[0] = (int)(getBildschirmBreite()*0.05);
        gridBasePos[1] = (int)(getBildschirmHoehe()*0.156);
        fieldEdgeSize = getBildschirmBreite()/10;
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
        noteGrids = new ArrayList<>();
        editTexts = new ArrayList<>();
        keyPad = new KeyPad(playActivity,game,this, playViewModel);
    }

    @SuppressLint("ClickableViewAccessibility") //Warnung unterdrücken, dass man performClick nicht überschreibt
    public void generateGrid(GridLayout grid,GridLayout gridMask, RelativeLayout playScreen){
        for(int row = 0; row <= 8; row++){
            for(int column = 0; column <= 8; column++){
                View maskView = new View(context);
                initMaskView(maskView);

                EditText editText = new EditText(context);
                editTextInit(editText,row,column);
                if(game.getField()[row][column] != 0){
                    editText.setText(String.valueOf(game.getField()[row][column]));
                    editText.setEnabled(false);
                    game.getFreeCellsArray()[row][column] = false;
                } else {
                    game.getFreeCellsArray()[row][column] = true;
                    GridLayout noteGrid = new GridLayout(context);
                    noteGridInit(noteGrid,row,column);
                    int noteNum = 1;
                    for(int noteRow = 0; noteRow <= 2; noteRow++){
                        for(int noteColumn = 0; noteColumn <= 2; noteColumn++) {
                            TextView note = new TextView(context);
                            noteInit(note,noteRow,noteColumn);
                            note.setText(String.valueOf(noteNum));
                            noteNum++;
                            View.OnTouchListener noteGridTouchListener =  onTouchListener(noteGrid,this);
                            noteGrid.setOnTouchListener(noteGridTouchListener);
                            noteGrid.addView(note);
                        }
                    }
                    playScreen.addView(noteGrid);
                    noteGrids.add(noteGrid);
                }
                editTexts.add(editText);
                View.OnTouchListener editTextTouchListener =  onTouchListener(editText,this);
                editText.setOnTouchListener(editTextTouchListener);
                hideKeyboardFrom(context,editText);
                grid.addView(editText);
                gridMask.addView(maskView);
            }
        }

        gridMask.setVisibility(View.INVISIBLE);

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

    private void initMaskView(View maskView) {
        maskView.setBackground(AppCompatResources.getDrawable(context, R.drawable.edit_text_field_border_black));
        ViewGroup.LayoutParams maskParams = new ViewGroup.LayoutParams(getBildschirmBreite()/10,getBildschirmBreite()/10);
        maskView.setLayoutParams(maskParams);
    }

    private void editTextInit(EditText editText,int row, int column){
        editText.setId(game.getHelper().coordinateAsOneNumber(row,column));
        editText.setGravity(Gravity.CENTER);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setTextColor(Color.BLACK);
        editText.setText("");
        editText.setBackground(AppCompatResources.getDrawable(context, R.drawable.edit_text_field_border_black));
        ViewGroup.LayoutParams editTextParams = new ViewGroup.LayoutParams(getBildschirmBreite()/10,getBildschirmBreite()/10);
        editText.setLayoutParams(editTextParams);
        editText.setCursorVisible(false);
    }

    private void noteGridInit(GridLayout noteGrid, int row, int column){
        noteGrid.setId(game.getHelper().coordinateAsOneNumber(row, column));
        noteGrid.setX(gridBasePos[0]+column*fieldEdgeSize);
        noteGrid.setY(gridBasePos[1]+row*fieldEdgeSize);
        noteGrid.setVisibility(View.INVISIBLE);
        noteGrid.setColumnCount(3);
        noteGrid.setRowCount(3);
    }

    private void noteInit(TextView note,int noteRow, int noteColumn){
        ViewGroup.LayoutParams noteParams = new ViewGroup.LayoutParams(getBildschirmBreite()/30,getBildschirmBreite()/30);
        note.setLayoutParams(noteParams);
        note.setVisibility(View.INVISIBLE);
        note.setGravity(Gravity.CENTER);
        note.setTextColor(Color.BLACK);
        note.setTextSize((float)(fieldEdgeSize*0.1));
        if(noteRow == 1 && noteColumn == 1) {
            note.setBackground(AppCompatResources.getDrawable(context, R.drawable.whiteshape));
        } else {
            note.setBackground(noteBackgroundSelector[noteRow][noteColumn]);
        }
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
    public View.OnTouchListener onTouchListener(EditText editText, PlayGraphic graphic){
        return (view, motionEvent) -> {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    graphic.setFocusedEditText(editText);
                    if(playViewModel.getLastFocusedCell() != null) {
                        playViewModel.getLastFocusedCell().setBackground(AppCompatResources.getDrawable(context, R.drawable.edit_text_field_border_black));
                    }
                    editText.setBackgroundResource(R.drawable.edit_text_focused);
                    return true;

                case MotionEvent.ACTION_UP:
                    playViewModel.setLastFocusedCell(editText);
                    view.performClick();
                    return true;
            }
            return false;
        };
    }
    public View.OnTouchListener onTouchListener(GridLayout noteGrid, PlayGraphic graphic){
        return (view, motionEvent) -> {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    graphic.setFocusedNoteGrid(noteGrid);
                    if(playViewModel.getLastFocusedGrid() != null){
                        playViewModel.getLastFocusedGrid().setBackgroundResource(R.drawable.edit_text_field_border_black);
                    }
                    noteGrid.setBackgroundResource(R.drawable.edit_text_focused);
                    return true;
                case MotionEvent.ACTION_UP:
                    playViewModel.setLastFocusedGrid(noteGrid);
                    view.performClick();
                    return true;
            }
            return false;
        };
    }

    public static void hideKeyboardFrom(Context context, View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
