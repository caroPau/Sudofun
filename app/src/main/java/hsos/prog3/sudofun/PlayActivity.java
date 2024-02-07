package hsos.prog3.sudofun;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class PlayActivity extends AppCompatActivity {
    private Level level;
    private static int[][] field;
    private Timer timer;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        field = SudokuCreator.createSudoku(level);
        handler = new Handler(Looper.myLooper());
        timer = new Timer(handler);
        timer.start();
    }

    private static void setDigit(int digit, int row, int column){
        if(SudokuHelper.isValid(row, column, digit, field)){
            field[row][column] = digit;
        }else{
            //TODO: Visueller Effekt
        }
    }

}
