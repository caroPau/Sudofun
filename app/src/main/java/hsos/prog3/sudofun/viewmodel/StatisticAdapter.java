package hsos.prog3.sudofun.viewmodel;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder> {
    @NonNull
    @Override
    public StatisticAdapter.StatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticAdapter.StatisticViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class StatisticViewHolder extends RecyclerView.ViewHolder {
        public StatisticViewHolder(View itemView) {
            super(itemView);
        }
    }
}
