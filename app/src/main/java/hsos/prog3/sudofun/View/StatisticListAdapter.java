package hsos.prog3.sudofun.View;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import hsos.prog3.sudofun.database.UserEntity;
import hsos.prog3.sudofun.viewmodel.StatisticAdapter;

public class StatisticListAdapter extends ListAdapter<UserEntity, StatisticViewHolder> {

    public StatisticListAdapter(@NonNull DiffUtil.ItemCallback<UserEntity> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public StatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return StatisticViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticViewHolder holder, int position) {
        UserEntity current = getItem(position);
        holder.bind(current.username);
    }

    public static class StatisticDiff extends DiffUtil.ItemCallback<UserEntity>{

        @Override
        public boolean areItemsTheSame(@NonNull UserEntity oldItem, @NonNull UserEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserEntity oldItem, @NonNull UserEntity newItem) {
            return oldItem.username.equals(newItem.username);
        }
    }
}
