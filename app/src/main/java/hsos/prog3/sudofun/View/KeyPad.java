package hsos.prog3.sudofun.View;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import hsos.prog3.sudofun.model.Play;
import hsos.prog3.sudofun.viewmodel.PlayActivity;
import hsos.prog3.sudofun.viewmodel.PlayGraphic;

public class KeyPad {
    PlayActivity playActivity;
    Play game;
    PlayGraphic graphic;
    public KeyPad(PlayActivity playActivity, Play game, PlayGraphic graphic){
        this.playActivity = playActivity;
        this.game = game;
        this.graphic = graphic;
        /**
         * Registrieren des onClickListeners auf die Buttons im Tastenfeld
         *
         * @author C. Paul
         */
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
     * Funktion für das Registrieren des onClickListeners auf einen Button
     * @author C. Paul
     */
    private void registerListener(Button button){
        View.OnClickListener numbClickListener =  onClickListener(button,graphic,game);
        button.setOnClickListener(numbClickListener);
    }
    /**
     * Funktion für die Rückgabe der indizierten Notiz im GridLayout
     *
     * @author C. Paul
     */
    private View getNote(int index,GridLayout noteGrid){
        return noteGrid.getChildAt(index-1);
    }

    /**
     * OnClickListener für alle Zahlen auf dem Tastenfeld
     * je nach Modus wird der Inhalt des zuletzt fokussierten Views mit dem jeweiligen Zahlenwert überschrieben
     *  bzw. gelöscht
     * @author C. Paul
     */

    public View.OnClickListener onClickListener(Button button,PlayGraphic graphic, Play game){
        return view -> {
            CharSequence buttonText = button.getText();
            EditText focusedEditText = graphic.getFocusedEditText();
            GridLayout focusedNoteGrid = graphic.getFocusedNoteGrid();
            View note = null;
            /**
             * Behandlung des Falls, bei dem der Notizmodus aktiviert ist
             * @author C. Paul
             */
            if(game.isNoteMode()){
                /**
                 * löschen aller Notizen bei "clear" als Eingabe
                 * @author C. Paul
                 */
                if(buttonText.toString().equals("clear")){
                    for(int i = 1; i<=9;i++){
                        note = getNote(i,focusedNoteGrid);
                        note.setVisibility(View.INVISIBLE);
                    }
                    return;
                }
                else{
                    /**
                     * Parsen der Notiz, die getoggled werden soll
                     * @author C. Paul
                     */
                    note = getNote(Integer.parseInt(buttonText.toString()),focusedNoteGrid);
                }
                /**
                 * Toggeln der jeweiligen Notiz
                 * @author C. Paul
                 */
                if(note != null) {
                    if (note.getVisibility() == View.INVISIBLE) {
                        note.setVisibility(View.VISIBLE);
                    } else {
                        note.setVisibility(View.INVISIBLE);
                    }
                }
            }else{
                /**
                 * Behandlung des Falls, bei dem der Notizmodus deaktiviert ist
                 *
                 * @author C. Paul
                 */
                if(buttonText.toString().equals("clear")) {
                    focusedEditText.setText("");
                }
                else{
                    focusedEditText.setText(buttonText);
                }
            }
        };
    }
}
