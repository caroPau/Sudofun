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
import hsos.prog3.sudofun.model.LevelEnum;
import hsos.prog3.sudofun.model.UserEntity;

/**
 * Adapter für dynamische Erzeugung einer Liste für Ansicht der besten Spieler.
 *
 * @author M.Paul
 */
public class HighscoreAdapter extends RecyclerView.Adapter<HighscoreAdapter.HighscoreViewHolder> {
    private final LevelEnum levelEnum;
    private final List<UserEntity> highscoreList;


    // Konstruktor, um die Liste von Highscores zu übergeben
    public HighscoreAdapter(List<UserEntity> highscoreList, LevelEnum levelEnum) {
        this.highscoreList = highscoreList;
        this.levelEnum = levelEnum;
    }

    // ViewHolder-Klasse für jedes Listenelement
    public static class HighscoreViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameTextView;
        public final TextView timeTextView;

        public HighscoreViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.player_name);
            timeTextView = view.findViewById(R.id.player_score);
        }
    }

    /**
     * Erstellt einen neuen ViewHolder, der die Ansichten für jedes Listenelement enthält.
     *
     * @param parent   Die übergeordnete ViewGroup, in die der neue ViewHolder eingefügt werden soll.
     * @param viewType Der Ansichtstyp des neuen ViewHolders.
     * @return Der erstellte ViewHolder, der die Ansichten für jedes Listenelement enthält.
     */
    @NonNull
    @Override
    public HighscoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_statistic, parent, false);
        return new HighscoreViewHolder(itemView);
    }

    /**
     * Bindet die Daten an die einzelnen Listenelemente
     *
     * @param holder   Der ViewHolder, der die Ansichten für das Listenelement enthält.
     * @param position Die Position des Listenelements in der Liste.
     */

    @Override
    public void onBindViewHolder(@NonNull HighscoreViewHolder holder, int position) {
        UserEntity user = highscoreList.get(position);
        String formattedText = String.format(Locale.getDefault(), "%d. %s", position + 1, user.username);
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
