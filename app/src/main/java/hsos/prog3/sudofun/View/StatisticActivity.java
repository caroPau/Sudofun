package hsos.prog3.sudofun.View;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.UserEntity;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.Statistic;
import hsos.prog3.sudofun.viewmodel.DataViewModel;

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
        statistic.getDataViewModel().findByName(username).observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(UserEntity user) {
                if (user != null) {
                    statistic.setUser(user);
                } else {
                    Log.w("INFOTAG", "User " + username + " not found.");
                }
            }
        });
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
