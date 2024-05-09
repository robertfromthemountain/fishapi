package com.example.fishapi.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fishapi.R;
import com.example.fishapi.dtos.ImgSrcSet;
import com.example.fishapi.dtos.ImgSrcSetDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fishapi.dtos.FishItem;

import java.util.List;

public class FishAdapter extends RecyclerView.Adapter<FishAdapter.FishViewHolder> {

    private Context context;
    private List<FishItem> fishList;

    private Gson gson;

    public FishAdapter(Context context, List<FishItem> fishList) {
        this.context = context;
        this.fishList = fishList;

        this.gson = new GsonBuilder()
                .registerTypeAdapter(ImgSrcSet.class, new ImgSrcSetDeserializer())
                .create();

    }

    public void updateData(List<FishItem> newFishList) {
        fishList.clear();
        fishList.addAll(newFishList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fish, parent, false);
        return new FishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FishViewHolder holder, int position) {
        FishItem fish = fishList.get(position);

        holder.fishNameTextView.setText(fish.getName());

        ImgSrcSet imgSrcSet = gson.fromJson(fish.getImgSrcSetElement(), ImgSrcSet.class);

        if (imgSrcSet != null && imgSrcSet.getImgSrc2x() != null) {
            Glide.with(context)
                    .load(imgSrcSet.getImgSrc2x())
                    .placeholder(R.drawable.placeholder_image)
                    .centerCrop()
                    .into(holder.imageViewFish);
        } else {
            Glide.with(context)
                    .load(R.drawable.placeholder_image)
                    .placeholder(R.drawable.placeholder_image)
                    .centerCrop()
                    .into(holder.imageViewFish);
        }
    }


    @Override
    public int getItemCount() {
        return fishList.size();
    }

    public class FishViewHolder extends RecyclerView.ViewHolder {

        private TextView fishNameTextView;
        private ImageView imageViewFish;



        public FishViewHolder(@NonNull View itemView) {
            super(itemView);
            fishNameTextView = itemView.findViewById(R.id.fishNameTextView);
            imageViewFish = itemView.findViewById(R.id.imageViewFish);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                        FishItem fishItem = fishList.get(position);
                        itemClickListener.onItemClick(fishItem);
                    }
                }
            });
        }

    }

    public FishItem getItem(int position) {
        return fishList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(FishItem fishItem);
    }

    private OnItemClickListener itemClickListener;

}

