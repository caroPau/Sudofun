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
        Bundle bundle = getIntent().getExtras();
        Level level = Level.valueOf(bundle.getString("level"));
        user = bundle.getSerializable("user", User.class);
        Statistic statistic = new Statistic(user);

    }

    /**
     * Initialisiert die Variablen von Statistic mit den zum Level passenden Werten des Users
     *
     * @param statistic Die Statistik die hier initialisiert wird
     * @param level Der Schwierigkeitsgrad für den wir die Statistiken wollen
     *
     * @author C. Paul
     */
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
