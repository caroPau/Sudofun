package hsos.prog3.sudofun.viewmodel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.Statistic;
import hsos.prog3.sudofun.model.User;

public class StatisticActivity extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        //TODO: User aus Intent holen
        //TODO: Level aus dem Intent holen
        Statistic statistic = new Statistic(user);

    }

    private void getValuesStatistic(Statistic statistic, Level level) {
        switch (level) {
            case EASY:
                statistic.setBestTime(user.getBestTimeEasy());
                statistic.setTotalGames(user.getEasyGames());
                break;
            case MEDIUM:
                statistic.setBestTime(user.getBestTimeMedium());
                statistic.setTotalGames(user.getMediumGames());
                break;
            case HARD:
                statistic.setBestTime(user.getBestTimeHard());
                statistic.setTotalGames(user.getHardGames());
                break;
        }
    }
}
