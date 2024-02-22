package hsos.prog3.sudofun.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hsos.prog3.sudofun.R;
import hsos.prog3.sudofun.model.LevelEnum;
import hsos.prog3.sudofun.model.UserEntity;

/**
 * Adapter für dynamische Erzeugung einer Liste für Ansicht der besten Spieler.
 */
public class HighscoreAdapter extends RecyclerView.Adapter<HighscoreAdapter.HighscoreViewHolder> {

    private List<UserEntity> highscoreList;
    private LevelEnum levelEnum;

    // Konstruktor, um die Liste von Highscores zu übergeben
    public HighscoreAdapter(List<UserEntity> highscoreList, LevelEnum levelEnum) {
        this.highscoreList = highscoreList;
        this.levelEnum = levelEnum;
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
        String formattedText = String.format("%d. %s", position + 1, user.username);
        holder.nameTextView.setText(formattedText);
        long score = 0;

        switch (levelEnum) {
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
            String time = String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
            holder.timeTextView.setText(String.valueOf(time));
        } else {
            holder.timeTextView.setText("00:00");
        }
    }

    @Override
    public int getItemCount() {
        return highscoreList.size();
    }
}
