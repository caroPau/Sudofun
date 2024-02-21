package hsos.prog3.sudofun.View;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.appcompat.content.res.AppCompatResources;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Play;
import hsos.prog3.sudofun.viewmodel.PlayViewModel;

public class KeyPad {
    PlayActivity playActivity;
    Play game;
    PlayGraphic graphic;
    PlayViewModel playViewModel;

    public KeyPad(PlayActivity playActivity, Play game, PlayGraphic graphic, PlayViewModel playViewModel) {
        this.playActivity = playActivity;
        this.game = game;
        this.graphic = graphic;
        this.playViewModel = playViewModel;
        registerAllListeners();
    }

    /**
     * Funktion für das Registrieren des onClickListeners auf einen Button
     */
    private void registerListener(Button button) {
        View.OnClickListener numbClickListener = onClickListener(button, graphic, game);
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

    public View.OnClickListener onClickListener(Button button, PlayGraphic graphic, Play game) {
        return view -> {
            CharSequence buttonText = button.getText();
            EditText focusedEditText = graphic.getFocusedEditText();
            GridLayout focusedNoteGrid = graphic.getFocusedNoteGrid();
            View note = null;

            if (focusedEditText == null) {
                return;
            }
            /*
             * Behandlung des Falls, bei dem der Notizmodus aktiviert ist
             */
            if (game.isNoteMode()) {
                /*
                 * löschen aller Notizen bei "clear" als Eingabe
                 */
                if (buttonText.toString().equals("clear")) {
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
                    game.getHelper().numberToCoordinate(focusedNoteGrid.getId(), game);
                    boolean temp = game.getFreeCellsArray()[game.getRowHint()][game.getColumnHint()];
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
                if (buttonText.toString().equals("clear")) {
                    if (!focusedEditText.getText().toString().equals("")) {
                        game.getHelper().numberToCoordinate(focusedEditText.getId(), game);
                        playViewModel.reactToClear(game);
                        System.out.println(game.getFreeCells());
                        focusedEditText.setText("");
                        game.getHelper().numberToCoordinate(focusedEditText.getId(), game);
                        game.getFreeCellsArray()[game.getRowHint()][game.getColumnHint()] = true;
                    }
                } else {
                    focusedEditText.setText(buttonText);
                    game.getHelper().numberToCoordinate(focusedEditText.getId(), game);
                    if(playViewModel.reactToNewNumber(game, game.getRowHint(), game.getColumnHint(), Integer.parseInt(buttonText.toString()))) {
                        focusedEditText.setTextColor(Color.BLACK);
                        game.getFreeCellsArray()[game.getRowHint()][game.getColumnHint()] = false;
                    }else{
                        focusedEditText.setTextColor(Color.RED);
                    }

                }
                if (game.getFreeCells() == 0) {
                    playActivity.endGame();
                }
            }
        };

    }
}
