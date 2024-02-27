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
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    public PlayGraphic(PlayActivity playActivity, Context context, PlayViewModel playViewModel) {
        this.context = context;
        this.playViewModel = playViewModel;
        gridBasePos = new int[2];
        fieldEdgeSize = getBildschirmBreite() / 10;
        gridLineStrength = (int) (getBildschirmBreite() * 0.01);
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

    /**
     * Generiert das Sudoku-Raster mit den entsprechenden Zellen und Notizbereichen.
     *
     * @param grid           Das GridLayout für das Haupt-Sudoku-Raster.
     * @param gridMask       Das GridLayout für das Masken-Raster.
     * @param loadedGameView Die RelativeLayout, die das gesamte Spiel enthält.
     */
    public void generateGrid(GridLayout grid, GridLayout gridMask, RelativeLayout loadedGameView) {
        // Position des Grids wird initialisiert
        gridBasePos[0] = (int) (grid.getX() - 4.5 * fieldEdgeSize);
        gridBasePos[1] = (int) grid.getY();
        // Schleife zur Erzeugung der Zellen des Sudoku-Grids
        for (int row = 0; row <= 8; row++) {
            for (int column = 0; column <= 8; column++) {
                // Erzeugt eine Maske für das Grid
                View maskView = new View(context);
                initMaskView(maskView);
                // Erzeugt ein EditText-Feld für jede Zelle
                EditText editText = new EditText(context);
                editTextInit(editText, row, column);
                // Wenn die Zelle schon eine Zahl enthält, wird diese im EditText eingesetzt und das EditText deaktiviert
                if (playViewModel.getField()[row][column] != 0) {
                    editText.setText(String.valueOf(playViewModel.getField()[row][column]));
                    editText.setEnabled(false);
                    playViewModel.getFreeCellsArray()[row][column] = false;
                    // Wenn nicht
                } else {
                    playViewModel.getFreeCellsArray()[row][column] = true;
                    // für die Zelle wird ein noteGrid erstellt
                    GridLayout noteGrid = new GridLayout(context);
                    noteGridInit(noteGrid, row, column);
                    // die einzelnen Zellen des Grids werden erstellt
                    int noteNum = 1;
                    for (int noteRow = 0; noteRow <= 2; noteRow++) {
                        for (int noteColumn = 0; noteColumn <= 2; noteColumn++) {
                            TextView note = new TextView(context);
                            noteInit(note, noteNum);
                            noteNum++;
                            noteGrid.addView(note);
                        }
                    }
                    // noteGrid wird zum Spielansicht und zur noteGrid-
                    // liste hinzugefügt
                    loadedGameView.addView(noteGrid);
                    noteGrids.add(noteGrid);
                }
                // EditText wird zum Spielfeld-Grid und zur Liste der Edit-Texts hinzugefügt
                editTexts.add(editText);
                grid.addView(editText);
                // Maske wird zum MaskenGrid hinzugefügt
                gridMask.addView(maskView);
            }
        }
        // Masken Grid wird unsichtbar gemacht
        gridMask.setVisibility(View.INVISIBLE);

        //horizontal gridLine1
        generateGridLine(loadedGameView, 9 * fieldEdgeSize, gridLineStrength
                , gridBasePos[0], gridBasePos[1] + 3 * fieldEdgeSize - gridLineStrength / 2);
        //horizontal gridLine2
        generateGridLine(loadedGameView, 9 * fieldEdgeSize, gridLineStrength
                , gridBasePos[0], gridBasePos[1] + 6 * fieldEdgeSize - gridLineStrength / 2);
        //vertical gridLine1
        generateGridLine(loadedGameView, gridLineStrength, 9 * fieldEdgeSize
                , gridBasePos[0] + 3 * fieldEdgeSize - gridLineStrength / 2, gridBasePos[1]);
        //vertical gridLine2
        generateGridLine(loadedGameView, gridLineStrength, 9 * fieldEdgeSize
                , gridBasePos[0] + 6 * fieldEdgeSize - gridLineStrength / 2, gridBasePos[1]);
    }

    /**
     * Initialisiert die Maske für eine Zelle im Grid
     *
     * @param maskView Die View, die als Maske für die Zelle dient.
     */
    private void initMaskView(View maskView) {
        maskView.setBackground(AppCompatResources.getDrawable(context, R.drawable.edit_text_field_border_black));
        ViewGroup.LayoutParams maskParams = new ViewGroup.LayoutParams(fieldEdgeSize, fieldEdgeSize);
        maskView.setLayoutParams(maskParams);
    }

    /**
     * Initialisiert das EditText-Feld für eine Zelle im Raster.
     *
     * @param editText Das EditText-Feld für die Zelle.
     * @param row      Die Zeilennummer der Zelle im Raster.
     * @param column   Die Spaltennummer der Zelle im Raster.
     */
    @SuppressLint("ClickableViewAccessibility")
    //Warnung unterdrücken, dass man im Listener performClick nicht überschreibt
    private void editTextInit(EditText editText, int row, int column) {
        editText.setId(playViewModel.getHelper().coordinateAsOneNumber(row, column));
        editText.setForegroundGravity(Gravity.CENTER);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setTextColor(Color.BLACK);
        editText.setText("");
        editText.setTextSize(15);
        editText.setBackground(AppCompatResources.getDrawable(context, R.drawable.edit_text_field_border_black));
        ViewGroup.LayoutParams editTextParams = new ViewGroup.LayoutParams(fieldEdgeSize, fieldEdgeSize);
        editText.setLayoutParams(editTextParams);
        editText.setGravity(Gravity.CENTER);
        editText.setCursorVisible(false);
        View.OnTouchListener editTextTouchListener = onTouchListener(editText);
        editText.setOnTouchListener(editTextTouchListener);
    }

    /**
     * Initialisiert das Raster für Notizen in einer Zelle.
     *
     * @param noteGrid Das Raster für Notizen in der Zelle.
     * @param row      Die Zeilennummer der Zelle im Raster.
     * @param column   Die Spaltennummer der Zelle im Raster.
     */
    @SuppressLint("ClickableViewAccessibility")
    //Warnung unterdrücken, dass man im Listener performClick nicht überschreibt
    private void noteGridInit(GridLayout noteGrid, int row, int column) {
        noteGrid.setId(playViewModel.getHelper().coordinateAsOneNumber(row, column));
        noteGrid.setX(gridBasePos[0] + column * fieldEdgeSize);
        noteGrid.setY(gridBasePos[1] + row * fieldEdgeSize);
        noteGrid.setVisibility(View.INVISIBLE);
        noteGrid.setColumnCount(3);
        noteGrid.setRowCount(3);
        View.OnTouchListener noteGridTouchListener = onTouchListener(noteGrid, this);
        noteGrid.setOnTouchListener(noteGridTouchListener);
    }

    /**
     * Initialisiert eine Notiz in einem Notiz-Raster.
     *
     * @param note    Die TextView, die die Notiz darstellt.
     * @param noteNum Die Nummer der Notiz.
     */
    private void noteInit(TextView note, int noteNum) {
        ViewGroup.LayoutParams noteParams = new ViewGroup.LayoutParams(fieldEdgeSize / 3, fieldEdgeSize / 3);
        note.setLayoutParams(noteParams);
        note.setVisibility(View.INVISIBLE);
        note.setText(String.valueOf(noteNum));
        note.setGravity(Gravity.CENTER);
        note.setTextColor(Color.BLACK);
        note.setTextSize((float) (fieldEdgeSize * 0.1));
        note.setBackground(AppCompatResources.getDrawable(context, R.drawable.transparentshape));
    }

    /**
     * Generiert eine horizontale oder vertikale Gitterlinie auf dem Spielfeld.
     *
     * @param playScreen Die RelativeLayout, auf der die Gitterlinie angezeigt wird.
     * @param width      Die Breite der Gitterlinie.
     * @param height     Die Höhe der Gitterlinie.
     * @param x          Die X-Position der Gitterlinie.
     * @param y          Die Y-Position der Gitterlinie.
     */
    private void generateGridLine(RelativeLayout playScreen, int width, int height, int x, int y) {
        View gridLine = new View(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        gridLine.setBackground(AppCompatResources.getDrawable(context, R.drawable.blackshape));
        gridLine.setLayoutParams(layoutParams);
        gridLine.setX(x);
        gridLine.setY(y);
        playScreen.addView(gridLine);
    }

    /**
     * Erzeugt einen Touch-Listener für eine EditText-View.
     *
     * @param editText Die EditText-View, für die der Touch-Listener erstellt wird.
     * @return Der Touch-Listener für die EditText-View.
     */
    public View.OnTouchListener onTouchListener(EditText editText) {
        return (view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    focusedEditText = editText;
                    if (playViewModel.getLastFocusedCell() != null) {
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

    /**
     * Erzeugt einen Touch-Listener für ein Notiz-Gitter auf dem Spielfeld.
     *
     * @param noteGrid Das GridLayout des Notiz-Gitters.
     * @param graphic  Die Instanz der PlayGraphic-Klasse.
     * @return Der Touch-Listener für das Notiz-Gitter.
     */
    public View.OnTouchListener onTouchListener(GridLayout noteGrid, PlayGraphic graphic) {
        return (view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    graphic.setFocusedNoteGrid(noteGrid);
                    if (playViewModel.getLastFocusedGrid() != null) {
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
