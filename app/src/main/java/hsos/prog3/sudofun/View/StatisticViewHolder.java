package hsos.prog3.sudofun.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import hsos.prog3.sudofun.R;

public class StatisticViewHolder extends RecyclerView.ViewHolder {
    private final TextView highscoreItemView;

    public StatisticViewHolder(@NonNull View itemView) {
        super(itemView);
        this.highscoreItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String time){
        highscoreItemView.setText(time);
    }
    static StatisticViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new StatisticViewHolder(view);
    }
}
