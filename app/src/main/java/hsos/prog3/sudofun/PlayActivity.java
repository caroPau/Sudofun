package hsos.prog3.sudofun;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;


public class PlayActivity extends AppCompatActivity {
    private Level level;
    private static int[][] field;
    private Timer timer;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        field = SudokuCreator.createSudoku(level);
        handler = new Handler(Looper.myLooper());
        timer = new Timer(handler);
        timer.start();
    }

    private void setSupportActionBar(Toolbar myToolbar) {
        // TODO implementieren (WIE??)
        // https://developer.android.com/develop/ui/views/components/appbar/setting-up
    }

    private static void setDigit(int digit, int row, int column){
        if(SudokuHelper.isValid(row, column, digit, field)){
            field[row][column] = digit;
        }else{
            //TODO: Visueller Effekt
        }
    }

}
