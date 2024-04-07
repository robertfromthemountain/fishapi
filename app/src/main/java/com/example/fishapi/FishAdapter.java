package com.example.fishapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class FishAdapter extends RecyclerView.Adapter<FishAdapter.FishViewHolder> {

    private List<Fish> fishList;

    public FishAdapter(List<Fish> fishList) {
        this.fishList = fishList;
    }

    @NonNull
    @Override
    public FishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new FishViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FishViewHolder holder, int position) {
        Fish fish = fishList.get(position);
        holder.textViewFishName.setText(fish.getName());
    }

    @Override
    public int getItemCount() {
        return fishList.size();
    }

    public static class FishViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewFishName;

        public FishViewHolder(View itemView) {
            super(itemView);
            textViewFishName = itemView.findViewById(R.id.textViewFishName);
        }
    }
}
