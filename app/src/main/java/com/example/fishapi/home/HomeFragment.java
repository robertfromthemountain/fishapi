package com.example.fishapi.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fishapi.R;
import com.example.fishapi.clients.RetrofitClient;
import com.example.fishapi.dtos.FishItem;
import com.example.fishapi.dtos.ScientificClassification;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private FishAdapter fishAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFish);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fishAdapter = new FishAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(fishAdapter);

        NavController navController = NavHostFragment.findNavController(this);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(requireContext(), recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {

                    public void onItemClick(View view, int position) {
                        if (position >= 0 && position < fishAdapter.getItemCount()) {
                            FishItem selectedFish = fishAdapter.getItem(position);

                            if (selectedFish != null) {
                                ScientificClassification scientificClassification = selectedFish.getMeta().getScientificClassification();

                                //if (scientificClassification != null) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("id", selectedFish.getId());
                                    bundle.putString("name", selectedFish.getName());
                                    bundle.putString("url", selectedFish.getUrl());

                                    String ConservationStatus = selectedFish.getMeta().getConservationStatus();
                                    bundle.putString("conservationStatus", ConservationStatus != null ? ConservationStatus : "N/A");

                                    //bundle.putString("conservationStatus", selectedFish.getMeta().getConservationStatus());

                                    // Domain
                                    String domain = scientificClassification.getDomain();
                                    bundle.putString("domain", domain != null ? domain : "N/A");

                                    // Kingdom
                                    String kingdom = scientificClassification.getKingdom();
                                    bundle.putString("kingdom", kingdom != null ? kingdom : "N/A");

                                    // Phylum
                                    String phylum = scientificClassification.getPhylum();
                                    bundle.putString("phylum", phylum != null ? phylum : "N/A");

                                    // Class
                                    String classificationClass = scientificClassification.getClassType();
                                    bundle.putString("classificationClass", classificationClass != null ? classificationClass : "N/A");

                                    // Order
                                    String order = scientificClassification.getOrder();
                                    bundle.putString("order", order != null ? order : "N/A");

                                    // Superfamily
                                    String superfamily = scientificClassification.getSuperfamily();
                                    bundle.putString("superfamily", superfamily != null ? superfamily : "N/A");

                                    // Family
                                    String family = scientificClassification.getFamily();
                                    bundle.putString("family", family != null ? family : "N/A");

                                    bundle.putString("img_link", selectedFish.getImgSrcSetElement().toString());

                                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_fishDetailFragment, bundle);
                                /*} else {
                                    Log.e("HomeFragment", "Selected fish has null scientific classification");
                                }*/
                            } else {
                                Log.e("HomeFragment", "Selected fish is null");
                            }
                        }
                    }
                }));

        return view;
    }

    private void fetchFishData() {
        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitClient.fetchFishData(new RetrofitClient.FishDataCallback() {
            @Override
            public void onSuccess(List<FishItem> fetchedFishList) {
                updateFishList(fetchedFishList);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("RetrofitError", "Hiba történt", t);
                Toast.makeText(requireContext(), "Hiba történt: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchFishData();
    }

    public void updateFishList(List<FishItem> newFishList) {
        fishAdapter.updateData(newFishList);

        fishAdapter.notifyDataSetChanged();
    }


}