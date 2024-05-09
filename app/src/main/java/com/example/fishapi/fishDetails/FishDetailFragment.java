package com.example.fishapi.fishDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fishapi.R;
import com.example.fishapi.dtos.FishItem;
import com.example.fishapi.dtos.ImgSrcSet;
import com.google.gson.Gson;


public class FishDetailFragment extends Fragment {

    private TextView textDomain, textKingdom, textPhylum, textClass, textOrder, textSuperfamily, textFamily, textConservationStatus, textName;

    private ImageView imageViewFish;
    public static FishDetailFragment newInstance(Bundle bundle) {
        FishDetailFragment fragment = new FishDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fish_detail, container, false);

        textDomain = view.findViewById(R.id.textDomain);
        textKingdom = view.findViewById(R.id.textKingdom);
        textPhylum = view.findViewById(R.id.textPhylum);
        textClass = view.findViewById(R.id.textClass);
        textOrder = view.findViewById(R.id.textOrder);
        textSuperfamily = view.findViewById(R.id.textSuperfamily);
        textFamily = view.findViewById(R.id.textFamily);
        textConservationStatus = view.findViewById(R.id.textConservationStatus);
        textName = view.findViewById(R.id.textName);

        imageViewFish = view.findViewById(R.id.imageViewFish);

        Bundle bundle = getArguments();
        if (bundle != null) {

            int id = bundle.getInt("id", -1);
            String name = bundle.getString("name", "N/A");
            String url = bundle.getString("url", "N/A");
            String conservationStatus = bundle.getString("conservationStatus", "N/A");
            String domain = bundle.getString("domain", "N/A");
            String kingdom = bundle.getString("kingdom", "N/A");
            String phylum = bundle.getString("phylum", "N/A");
            String classificationClass = bundle.getString("classificationClass", "N/A");
            String order = bundle.getString("order", "N/A");
            String superfamily = bundle.getString("superfamily", "N/A");
            String family = bundle.getString("family", "N/A");


            textName.setText("Name: " + name);
            textDomain.setText("Domain: " + domain);
            textKingdom.setText("Kingdom: " + kingdom);
            textPhylum.setText("Phylum: " + phylum);
            textClass.setText("Class: " + classificationClass);
            textOrder.setText("Order: " + order);
            textSuperfamily.setText("Superfamily: " + superfamily);
            textFamily.setText("Family: " + family);
            textConservationStatus.setText("ConservationStatus: " + conservationStatus);

            String imgSrcSetJson = bundle.getString("img_link");
            if (imgSrcSetJson != null) {
                Gson gson = new Gson();
                ImgSrcSet imgSrcSet = gson.fromJson(imgSrcSetJson, ImgSrcSet.class);
                String imgUrl = imgSrcSet != null ? imgSrcSet.getImgSrc2x() : null;

                if (imgUrl != null && !imgUrl.isEmpty()) {
                    Glide.with(requireContext())
                            .load(imgUrl)
                            .placeholder(R.drawable.placeholder_image)
                            .centerCrop()
                            .into(imageViewFish);
                } else {
                    imageViewFish.setImageResource(R.drawable.placeholder_image);
                }
            }

        }


        return view;
    }
}
