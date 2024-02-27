package hsos.prog3.sudofun.view;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.viewmodel.PlayViewModel;

/**
 * Klasse für die Darstellung des KeyPads während eines laufenden Spiels.
 */
public class KeyPad {
    private final PlayActivity playActivity;
    private final PlayGraphic graphic;
    private final PlayViewModel playViewModel;

    public KeyPad(PlayActivity playActivity, PlayGraphic graphic, PlayViewModel playViewModel) {
        this.playActivity = playActivity;
        this.graphic = graphic;
        this.playViewModel = playViewModel;
        registerAllListeners();
    }

    /**
     * Funktion für das Registrieren des onClickListeners auf einen Button
     *
     * @author M.Paul
     */
    private void registerListener(Button button) {
        View.OnClickListener numbClickListener = onClickListener(button, graphic);
        button.setOnClickListener(numbClickListener);
    }

    /**
     * Funktion für das Registrieren der onClickListener für alle Buttons
     *
     * @author M.Paul
     */
    private void registerAllListeners() {
        registerListener(playActivity.getBinding().numbOne);
        registerListener(playActivity.getBinding().numbTwo);
        registerListener(playActivity.getBinding().numbThree);
        registerListener(playActivity.getBinding().numbFour);
        registerListener(playActivity.getBinding().numbFive);
        registerListener(playActivity.getBinding().numbSix);
        registerListener(playActivity.getBinding().numbSeven);
        registerListener(playActivity.getBinding().numbEight);
        registerListener(playActivity.getBinding().numbNine);
        registerListener(playActivity.getBinding().buttonClear);
    }

    /**
     * Funktion für die Rückgabe der indizierten Notiz im GridLayout
     *
     * @author: M.Paul
     */
    private View getNote(int index, GridLayout noteGrid) {
        return noteGrid.getChildAt(index - 1);
    }

    /**
     * OnClickListener für alle Zahlen auf dem Tastenfeld
     * je nach Modus wird der Inhalt des zuletzt fokussierten Views mit dem jeweiligen Zahlenwert überschrieben
     * bzw. gelöscht
     *
     * @author C.Paul
     */

    public View.OnClickListener onClickListener(Button button, PlayGraphic graphic) {
        return view -> {
            CharSequence buttonText = button.getText();
            EditText focusedEditText = graphic.getFocusedEditText();
            GridLayout focusedNoteGrid = graphic.getFocusedNoteGrid();
            View note;


            /*
             * Behandlung des Falls, bei dem der Notizmodus aktiviert ist
             */
            if (playViewModel.isNoteMode()) {
                if (focusedNoteGrid == null) {
                    return;
                }
                /*
                 * löschen aller Notizen wenn es sich um den clear Button handelt
                 */
                if (button.getId() == R.id.buttonClear) {
                    for (int i = 1; i <= 9; i++) {
                        note = getNote(i, focusedNoteGrid);
                        note.setVisibility(View.INVISIBLE);
                    }
                    return;
                } else {
                    /*
                     * Parsen der Notiz, die getoggled werden soll
                     */

                    note = getNote(Integer.parseInt(buttonText.toString()), focusedNoteGrid);
                }
                /*
                 * Toggeln der jeweiligen Notiz
                 */
                if (note != null) {
                    playViewModel.getHelper().numberToCoordinate(focusedNoteGrid.getId(), playViewModel);
                    boolean temp = playViewModel.getFreeCellsArray()[playViewModel.getCoordinateRow()][playViewModel.getCoordinateColumn()];
                    if (temp) {
                        if (note.getVisibility() == View.INVISIBLE) {
                            note.setVisibility(View.VISIBLE);
                        } else {
                            note.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            } else {
                /*
                 * Behandlung des Falls, bei dem der Notizmodus deaktiviert ist
                 */
                if (focusedEditText == null) {
                    return;
                }
                // der Clear-Button ist gedrückt
                if (button.getId() == R.id.buttonClear) {
                    /*
                     * wenn die gewählte Zelle nicht leer ist, wird die Funktion reactToClear-Methode des PlayViewModels aufegerufen
                     * und der Text des EditText zu "" geändert und das freeCellsArray an dieser Position auf true gesetzt
                     */
                    if (!focusedEditText.getText().toString().equals("")) {
                        playViewModel.getHelper().numberToCoordinate(focusedEditText.getId(), playViewModel);
                        playViewModel.reactToClear();
                        focusedEditText.setText("");
                        playViewModel.getHelper().numberToCoordinate(focusedEditText.getId(), playViewModel);
                        playViewModel.getFreeCellsArray()[playViewModel.getCoordinateRow()][playViewModel.getCoordinateColumn()] = true;
                    }
                } else { // einer der Zahlen-Buttons wurde gedrückt
                    playViewModel.getHelper().numberToCoordinate(focusedEditText.getId(), playViewModel);
                    focusedEditText.setText(buttonText);
                    // wenn reactToNewNumber true zurückgibt ist die Zahl korrekt und wird in Schwarz eingetragen
                    if (playViewModel.reactToNewNumber(playViewModel.getCoordinateRow(), playViewModel.getCoordinateColumn(), Integer.parseInt(buttonText.toString()))) {
                        focusedEditText.setTextColor(Color.BLACK);
                        playViewModel.getFreeCellsArray()[playViewModel.getCoordinateRow()][playViewModel.getCoordinateColumn()] = false;
                    } else {
                        // wenn sie false zurückgibt ist die Zahl an der stelle falsch und wird mit Rot eingetragen
                        focusedEditText.setTextColor(Color.RED);
                    }
                }
                // wenn keine freien Zellen mehr übrig sind, wird das Spiel beendet
                if (playViewModel.getFreeCells() == 0) {
                    playActivity.endGame();
                }
            }
        };

    }
}
