package hsos.prog3.sudofun.viewmodel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.database.UserEntity;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.Statistic;

public class StatisticActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        Bundle bundle = getIntent().getExtras();
        Level level = Level.valueOf(bundle.getString("level"));
        String username = bundle.getString("username");
        Statistic statistic = new Statistic();
        statistic.setDataViewModel(new ViewModelProvider(this).get(DataViewModel.class));
        statistic.setUser(statistic.getDataViewModel().findByName(username));
    }


    private List<UserEntity> getStatistics(Statistic statistic, Level level) {
        switch (level) {
            case EASY:
                return statistic.getDataViewModel().getHighscoresEasy();
            case MEDIUM:
                return statistic.getDataViewModel().getHighscoresMedium();
            case HARD:
                return statistic.getDataViewModel().getHighscoresHard();
            default:
                return null;
        }
    }
}
