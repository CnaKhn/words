package com.example.words;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {
    private List<Level> levels;

    public LevelAdapter(List<Level> levels) {
        this.levels = new ArrayList<>();
        this.levels = levels;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LevelViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_level, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {
        holder.bind(levels.get(position));
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_level_number);
        }

        public void bind(Level level) {
            tv.setText(String.valueOf(level.getId()));
        }
    }
}
