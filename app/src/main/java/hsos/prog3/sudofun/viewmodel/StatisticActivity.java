package hsos.prog3.sudofun.viewmodel;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Statistic;
import hsos.prog3.sudofun.model.User;

public class StatisticActivity extends AppCompatActivity {
    private Statistic statistic;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        //TODO: User aus Intent holen
        statistic = new Statistic(user);
        statistic.setHighScores(new HashSet<>());
    }


}
