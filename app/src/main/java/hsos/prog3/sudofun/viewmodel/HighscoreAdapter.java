package hsos.prog3.sudofun.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.Level;
import hsos.prog3.sudofun.model.UserEntity;

public class HighscoreAdapter extends RecyclerView.Adapter<HighscoreAdapter.HighscoreViewHolder> {

    private final List<UserEntity> highscoreList;
    private final Level level;

    // Konstruktor, um die Liste von Highscores zu übergeben
    public HighscoreAdapter(List<UserEntity> highscoreList, Level level) {
        this.highscoreList = highscoreList;
        this.level = level;
    }
    // ViewHolder-Klasse für jedes Listenelement
    public static class HighscoreViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView timeTextView;

        public HighscoreViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.player_name);
            timeTextView = view.findViewById(R.id.player_score);
        }
    }

    @NonNull
    @Override
    public HighscoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_statistic, parent, false);
        return new HighscoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HighscoreViewHolder holder, int position) {
        UserEntity user = highscoreList.get(position);
        String formattedText = String.format(Locale.getDefault(), "%d. %s", position + 1, user.username);
        holder.nameTextView.setText(formattedText);
        long score = 0;

        switch (level) {
            case EASY:
                score = user.highscoreEasy;
                break;
            case MEDIUM:
                score = user.highscoreMedium;
                break;
            case HARD:
                score = user.highscoreHard;
                break;
            default:
                holder.timeTextView.setText("N/A");
        }

        if (score != 0) {
            int secondsTemp = ((int) (score / 1000));
            int minutes = secondsTemp / 60;
            int seconds = secondsTemp - minutes * 60;
            String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            holder.timeTextView.setText(time);
        } else {
            holder.timeTextView.setText(R.string.time);
        }
    }

    @Override
    public int getItemCount() {
        return highscoreList.size();
    }
}
