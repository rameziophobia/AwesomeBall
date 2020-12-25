package com.example.crazyball.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crazyball.R;
import com.example.crazyball.model.tables.entities.LevelEntity;
import com.example.crazyball.model.tables.relations.LevelWithComponents;

import java.util.List;

public class LevelListAdapter extends RecyclerView.Adapter<LevelListAdapter.LevelViewHolder> {

    private final LayoutInflater inflater;
    private List<LevelWithComponents> levels;

    public LevelListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.level_recycler_view_item, parent, false);
        return new LevelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {
        if (levels != null) {
            LevelWithComponents currentLevel = levels.get(position);
            holder.levelItemTextView.setText(currentLevel.Level.getName());
            holder.numObstaclesTextView.setText(String.valueOf(currentLevel.components.size()));
        } else {
            holder.levelItemTextView.setText(R.string.dummy_level_name);
            holder.numObstaclesTextView.setText(R.string.dummy_num_components);
        }
    }

    void setLevels(List<LevelWithComponents> levels) {
        this.levels = levels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (levels != null) {
            return levels.size();
        }
        return 0;
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder {

        private final TextView levelItemTextView;
        private final TextView numObstaclesTextView;

        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            levelItemTextView = itemView.findViewById(R.id.level_name_text_view);
            numObstaclesTextView = itemView.findViewById(R.id.num_obstacles_text_view);
        }
    }


}
