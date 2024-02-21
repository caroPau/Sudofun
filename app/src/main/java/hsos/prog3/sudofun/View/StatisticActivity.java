package hsos.prog3.sudofun.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.databinding.ActivityStatisticBinding;
import hsos.prog3.sudofun.model.UserEntity;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.viewmodel.DataViewModel;
import hsos.prog3.sudofun.viewmodel.HighscoreAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;


public class StatisticActivity extends AppCompatActivity {
    private DataViewModel dataViewModel;
    private ActivityStatisticBinding binding;

    private List<UserEntity> bestUsers;
    private RecyclerView recyclerView;
    private HighscoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        Bundle bundle = getIntent().getExtras();
        Level level = Level.valueOf(bundle.getString("level"));
        String username = bundle.getString("username");
        binding = ActivityStatisticBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        recyclerView = binding.bestListRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        getStatistics(level).observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> retrievedUsers) {
                bestUsers = retrievedUsers;
                adapter = new HighscoreAdapter(bestUsers, level);
                recyclerView.setAdapter(adapter);
                binding.textViewTimer.setText(formatTime(bundle.getLong("time")));
            }
        });
    }


    private LiveData<List<UserEntity>> getStatistics(Level level) {
        LiveData<List<UserEntity>> users = null;
        switch (level) {
            case EASY:
                users = dataViewModel.getHighscoresEasy();
                break;
            case MEDIUM:
                users = dataViewModel.getHighscoresMedium();
                break;
            case HARD:
                users = dataViewModel.getHighscoresHard();
                break;
            default:
                return null;
        }
        return users;
    }
    public void startLevelActivity(View view) {
        Intent intent = new Intent(StatisticActivity.this, LevelActivity.class);
        startActivity(intent);
    }

    private String formatTime(Long time) {
        int secondsTemp = ((int) (time / 1000));
        int minutes = secondsTemp / 60;
        int seconds = secondsTemp - minutes * 60;
        return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }
}
