package com.example.fishapi.catchList;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fishapi.R;
import com.example.fishapi.dtos.Catch;

import java.util.List;

public class CatchAdapter extends RecyclerView.Adapter<CatchAdapter.CatchViewHolder> {

    private List<Catch> fishCatches;
    private Context context;
    private OnItemClickListener listener;

    public CatchAdapter(Context context, List<Catch> fishCatches) {
        this.context = context;
        this.fishCatches = fishCatches;
    }

    public interface OnItemClickListener {
        void onItemClick(Catch fishCatch);
    }


    @NonNull
    @Override
    public CatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catch, parent, false);
        return new CatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatchViewHolder holder, int position) {
        Catch fishCatch = fishCatches.get(position);

        holder.locationTextView.setText("Location: " + fishCatch.getLocation());
        holder.sizeTextView.setText("Size: " + fishCatch.getSize());
        holder.weightTextView.setText("Weight: " + fishCatch.getWeight());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(fishCatch);
            }
        });

        if (fishCatch.getImageUri() != null) {
            Uri imageUri = Uri.parse(fishCatch.getImageUri());
            Glide.with(context)
                    .load(imageUri)
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return fishCatches != null ? fishCatches.size() : 0;
    }

    public class CatchViewHolder extends RecyclerView.ViewHolder {

        TextView locationTextView;
        TextView sizeTextView;
        TextView weightTextView;
        ImageView imageView;

        CatchViewHolder(@NonNull View itemView) {
            super(itemView);
            locationTextView = itemView.findViewById(R.id.catchLocationTextView);
            sizeTextView = itemView.findViewById(R.id.catchSizeTextView);
            weightTextView = itemView.findViewById(R.id.catchWeightTextView);
            imageView = itemView.findViewById(R.id.catchImageView);

        }
    }

    public Catch getItem(int position) {
        return fishCatches.get(position);
    }

}
