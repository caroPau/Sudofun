package hsos.prog3.sudofun.View;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.viewmodel.PlayViewModel;

public class KeyPad {
    PlayActivity playActivity;
    PlayGraphic graphic;
    PlayViewModel playViewModel;

    public KeyPad(PlayActivity playActivity, PlayGraphic graphic, PlayViewModel playViewModel) {
        this.playActivity = playActivity;
        this.graphic = graphic;
        this.playViewModel = playViewModel;
        registerAllListeners();
    }

    /**
     * Funktion für das Registrieren des onClickListeners auf einen Button
     */
    private void registerListener(Button button) {
        View.OnClickListener numbClickListener = onClickListener(button, graphic);
        button.setOnClickListener(numbClickListener);
    }

    /**
     * Funktion für das Registrieren der onClickListener für alle Buttons
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
        registerListener(playActivity.getBinding().clearButton);
    }

    /**
     * Funktion für die Rückgabe der indizierten Notiz im GridLayout
     */
    private View getNote(int index, GridLayout noteGrid) {
        return noteGrid.getChildAt(index - 1);
    }

    /**
     * OnClickListener für alle Zahlen auf dem Tastenfeld
     * je nach Modus wird der Inhalt des zuletzt fokussierten Views mit dem jeweiligen Zahlenwert überschrieben
     * bzw. gelöscht
     *
     */

    public View.OnClickListener onClickListener(Button button, PlayGraphic graphic) {
        return view -> {
            CharSequence buttonText = button.getText();
            EditText focusedEditText = graphic.getFocusedEditText();
            GridLayout focusedNoteGrid = graphic.getFocusedNoteGrid();
            View note = null;
            /*
             * Behandlung des Falls, bei dem der Notizmodus aktiviert ist
             */
            if (playViewModel.isNoteMode()) {
                /*
                 * löschen aller Notizen wenn es sich um den clear Button handelt
                 */
                if (button.getId()== R.id.clearButton) {
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
                if (button.getId()==R.id.clearButton) {
                    if (!focusedEditText.getText().toString().equals("")) {
                        playViewModel.getHelper().numberToCoordinate(focusedEditText.getId(), playViewModel);
                        playViewModel.reactToClear();
                        focusedEditText.setText("");
                        playViewModel.getHelper().numberToCoordinate(focusedEditText.getId(), playViewModel);
                        playViewModel.getFreeCellsArray()[playViewModel.getCoordinateRow()][playViewModel.getCoordinateColumn()] = true;
                    }
                } else {
                    playViewModel.getHelper().numberToCoordinate(focusedEditText.getId(), playViewModel);

                    focusedEditText.setText(buttonText);
                    if(playViewModel.reactToNewNumber(playViewModel.getCoordinateRow(), playViewModel.getCoordinateColumn(), Integer.parseInt(buttonText.toString()))) {
                        focusedEditText.setTextColor(Color.BLACK);
                        playViewModel.getFreeCellsArray()[playViewModel.getCoordinateRow()][playViewModel.getCoordinateColumn()] = false;
                    }else{
                        focusedEditText.setTextColor(Color.RED);
                    }

                }
                if (playViewModel.getFreeCells() == 0) {
                    playActivity.endGame();
                }
            }
        };

    }
}
