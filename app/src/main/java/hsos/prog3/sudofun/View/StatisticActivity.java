package hsos.prog3.sudofun.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
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
import hsos.prog3.sudofun.viewmodel.StatisticViewModel;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Activity der Ansicht für Statistik nach beendetem Spiel.
 */
public class StatisticActivity extends AppCompatActivity {
    private StatisticViewModel statisticViewModel;
    private DataViewModel dataViewModel;
    private ActivityStatisticBinding binding;

    private RecyclerView recyclerView;
    private HighscoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        statisticViewModel = new ViewModelProvider(this).get(StatisticViewModel.class);
        Bundle bundle = getIntent().getExtras();
        statisticViewModel.setLevel(Level.valueOf(bundle.getString("level")));
        binding = ActivityStatisticBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        recyclerView = binding.bestListRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        statisticViewModel.setUser(bundle.getSerializable("user", UserEntity.class));

        statisticViewModel.getStatistics(dataViewModel).observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> retrievedUsers) {
                statisticViewModel.setBestUsers(retrievedUsers);
                adapter = new HighscoreAdapter(statisticViewModel.getBestUsers(), statisticViewModel.getLevel());
                recyclerView.setAdapter(adapter);
                binding.textViewTimer.setText(formatTime(bundle.getLong("time")));
            }
        });
    }

    
    public void startLevelActivity(View view) {
        Intent intent = new Intent(StatisticActivity.this, LevelActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", statisticViewModel.getUser());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private String formatTime(Long time) {
        int secondsTemp = ((int) (time / 1000));
        int minutes = secondsTemp / 60;
        int seconds = secondsTemp - minutes * 60;
        return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }
    
    public void navigateLogout(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //FLAG entfernt alle Activities im Task BackStack, sodass nach dem Logout nicht zurück navigiert werden kann
        startActivity(intent);
        finishAffinity();
    }
}
