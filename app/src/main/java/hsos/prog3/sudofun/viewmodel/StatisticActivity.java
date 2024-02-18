package hsos.prog3.sudofun.viewmodel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.View.StatisticListAdapter;
import hsos.prog3.sudofun.database.UserEntity;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.Statistic;
import hsos.prog3.sudofun.model.User;

public class StatisticActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final StatisticListAdapter statisticListAdapter = new StatisticListAdapter(new StatisticListAdapter.StatisticDiff());
        recyclerView.setAdapter(statisticListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Bundle bundle = getIntent().getExtras();
        Level level = Level.valueOf(bundle.getString("level"));
        User user = bundle.getSerializable("user", User.class);
        Statistic statistic = new Statistic(user);
        statistic.setDataViewModel(new ViewModelProvider(this).get(DataViewModel.class));
        Objects.requireNonNull(getStatistics(statistic, level)).observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                for(UserEntity u: userEntities){
                    statistic.getDataViewModel().updateUser(u);
                }
            }
        });
    }


    private LiveData<List<UserEntity>> getStatistics(Statistic statistic, Level level) {
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
