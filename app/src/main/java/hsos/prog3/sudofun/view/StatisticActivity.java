package hsos.prog3.sudofun.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.databinding.ActivityStatisticBinding;
import hsos.prog3.sudofun.model.UserEntity;
import hsos.prog3.sudofun.model.LevelEnum;
import hsos.prog3.sudofun.viewmodel.DataViewModel;
import hsos.prog3.sudofun.viewmodel.HighscoreAdapter;
import hsos.prog3.sudofun.viewmodel.StatisticViewModel;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Activity der Ansicht für Statistik nach beendetem Spiel.
 */
public class StatisticActivity extends AppCompatActivity {
    private StatisticViewModel statisticViewModel;
    private ActivityStatisticBinding binding;

    private RecyclerView recyclerView;
    private HighscoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statisticViewModel = new ViewModelProvider(this).get(StatisticViewModel.class);
        Bundle bundle = getIntent().getExtras();
        statisticViewModel.setLevel(LevelEnum.valueOf(bundle.getString("level")));
        binding = ActivityStatisticBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        recyclerView = binding.bestListRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DataViewModel dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

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

    /**
     * Startet die LevelActivity, um ein neues Spiel zu beginnen.
     *
     * @param view Die angeklickte Ansicht.
     */
    public void startLevelActivity(View view) {
        Intent intent = new Intent(StatisticActivity.this, LevelActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", statisticViewModel.getUser());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Formatieren der Zeit in das Format "MM:SS".
     *
     * @param time Die Zeit in Millisekunden.
     * @return Die formatierte Zeit als String.
     */
    private String formatTime(Long time) {
        int secondsTemp = ((int) (time / 1000));
        int minutes = secondsTemp / 60;
        int seconds = secondsTemp - minutes * 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    /**
     * Navigiert zur LoginActivity, um sich auszuloggen.
     *
     * @param view Die angeklickte Ansicht.
     */
    public void navigateLogout(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //FLAG entfernt alle Activities im Task BackStack, sodass nach dem Logout nicht zurück navigiert werden kann
        startActivity(intent);
        finishAffinity();
    }
}
