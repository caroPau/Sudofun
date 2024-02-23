package hsos.prog3.sudofun.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.List;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.viewmodel.PlayViewModel;

/**
 * Klasse für die Generierung und Anzeige des Spielfeldes.
 */
public class PlayGraphic {
    private static int[] gridBasePos;
    private static int fieldEdgeSize;
    private final Context context;
    private final PlayViewModel playViewModel;
    private final List<EditText> editTexts;
    private final List<GridLayout> noteGrids;
    private EditText focusedEditText;
    private GridLayout focusedNoteGrid;

    public PlayGraphic(PlayActivity playActivity, Context context, PlayViewModel playViewModel){
        this.context = context;
        this.playViewModel = playViewModel;
        gridBasePos = new int[2];
        gridBasePos[0] = (int)(getBildschirmBreite()*0.05);  //Hardcoded, da getX und getY von grid nicht die richtige Lage liefern
        gridBasePos[1] = (int)(getBildschirmHoehe()*0.136);
        fieldEdgeSize = getBildschirmBreite()/10;
        gridLineStrength = (int)(getBildschirmBreite()*0.01);
        noteGrids = new ArrayList<>();
        editTexts = new ArrayList<>();
        new KeyPad(playActivity, this, playViewModel);
    }

    public List<EditText> getEditTexts() {
        return editTexts;
    }

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

    public EditText getFocusedEditText() {
        return focusedEditText;
    }

    public static int getBildschirmBreite() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getBildschirmHoehe() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void generateGrid(GridLayout grid,GridLayout gridMask, RelativeLayout loadedGameView){
        gridBasePos[0]=(int)(grid.getX()-4.5*fieldEdgeSize);
        gridBasePos[1]=(int)grid.getY();
        for(int row = 0; row <= 8; row++){
            for(int column = 0; column <= 8; column++){
                View maskView = new View(context);
                initMaskView(maskView);
                EditText editText = new EditText(context);
                editTextInit(editText,row,column);
                if(playViewModel.getField()[row][column] != 0){
                    editText.setText(String.valueOf(playViewModel.getField()[row][column]));
                    editText.setEnabled(false);
                    playViewModel.getFreeCellsArray()[row][column] = false;
                } else {
                    playViewModel.getFreeCellsArray()[row][column] = true;
                    GridLayout noteGrid = new GridLayout(context);
                    noteGridInit(noteGrid,row,column);
                    int noteNum = 1;
                    for(int noteRow = 0; noteRow <= 2; noteRow++){
                        for(int noteColumn = 0; noteColumn <= 2; noteColumn++) {
                            TextView note = new TextView(context);
                            noteInit(note, noteNum);
                            noteNum++;
                            noteGrid.addView(note);
                        }
                    }
                    loadedGameView.addView(noteGrid);
                    noteGrids.add(noteGrid);
                }
                editTexts.add(editText);
                grid.addView(editText);
                gridMask.addView(maskView);
            }
        }

        gridMask.setVisibility(View.INVISIBLE);

        //horizontal gridLine1
        generateGridLine(loadedGameView,9*fieldEdgeSize,gridLineStrength
                ,gridBasePos[0],gridBasePos[1]+3*fieldEdgeSize-gridLineStrength/2);
        //horizontal gridLine2
        generateGridLine(loadedGameView, 9*fieldEdgeSize,gridLineStrength
                ,gridBasePos[0],gridBasePos[1]+6*fieldEdgeSize-gridLineStrength/2);
        //vertical gridLine1
        generateGridLine(loadedGameView,gridLineStrength,9*fieldEdgeSize
                ,gridBasePos[0]+3*fieldEdgeSize-gridLineStrength/2,gridBasePos[1]);
        //vertical gridLine2
        generateGridLine(loadedGameView, gridLineStrength,9*fieldEdgeSize
                ,gridBasePos[0]+6*fieldEdgeSize-gridLineStrength/2,gridBasePos[1]);
    }

    private void initMaskView(View maskView) {
        maskView.setBackground(AppCompatResources.getDrawable(context, R.drawable.edit_text_field_border_black));
        ViewGroup.LayoutParams maskParams = new ViewGroup.LayoutParams(getBildschirmBreite()/10,getBildschirmBreite()/10);
        maskView.setLayoutParams(maskParams);
    }
    @SuppressLint("ClickableViewAccessibility") //Warnung unterdrücken, dass man im Listener performClick nicht überschreibt
    private void editTextInit(EditText editText,int row, int column){
        editText.setId(playViewModel.getHelper().coordinateAsOneNumber(row,column));
        editText.setGravity(Gravity.CENTER);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setTextColor(Color.BLACK);
        editText.setText("");
        editText.setBackground(AppCompatResources.getDrawable(context, R.drawable.edit_text_field_border_black));
        ViewGroup.LayoutParams editTextParams = new ViewGroup.LayoutParams(getBildschirmBreite()/10,getBildschirmBreite()/10);
        editText.setLayoutParams(editTextParams);
        editText.setCursorVisible(false);
        View.OnTouchListener editTextTouchListener =  onTouchListener(editText);
        editText.setOnTouchListener(editTextTouchListener);
    }

    @SuppressLint("ClickableViewAccessibility") //Warnung unterdrücken, dass man im Listener performClick nicht überschreibt
    private void noteGridInit(GridLayout noteGrid, int row, int column){
        noteGrid.setId(playViewModel.getHelper().coordinateAsOneNumber(row, column));
        noteGrid.setX(gridBasePos[0]+column*fieldEdgeSize);
        noteGrid.setY(gridBasePos[1]+row*fieldEdgeSize);
        noteGrid.setVisibility(View.INVISIBLE);
        noteGrid.setColumnCount(3);
        noteGrid.setRowCount(3);
        View.OnTouchListener noteGridTouchListener =  onTouchListener(noteGrid,this);
        noteGrid.setOnTouchListener(noteGridTouchListener);
    }

    private void noteInit(TextView note, int noteNum){
        ViewGroup.LayoutParams noteParams = new ViewGroup.LayoutParams(getBildschirmBreite()/30,getBildschirmBreite()/30);
        note.setLayoutParams(noteParams);
        note.setVisibility(View.INVISIBLE);
        note.setText(String.valueOf(noteNum));
        note.setGravity(Gravity.CENTER);
        note.setTextColor(Color.BLACK);
        note.setTextSize((float)(fieldEdgeSize*0.1));
        note.setBackground(AppCompatResources.getDrawable(context, R.drawable.transparentshape));
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
    public View.OnTouchListener onTouchListener(EditText editText){
        return (view, motionEvent) -> {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    focusedEditText = editText;
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
                    noteGrid.setBackgroundResource(R.drawable.edit_text_focused_notemode);
                    return true;
                case MotionEvent.ACTION_UP:
                    playViewModel.setLastFocusedGrid(noteGrid);
                    view.performClick();
                    return true;
            }
            return false;
        };
    }
}
