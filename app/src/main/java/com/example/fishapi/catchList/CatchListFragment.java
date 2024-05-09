package com.example.fishapi.catchList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fishapi.signUp.DatabaseHelper;
import com.example.fishapi.R;
import com.example.fishapi.login.SessionManager;
import com.example.fishapi.dtos.Catch;
import com.example.fishapi.home.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CatchListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CatchAdapter catchAdapter;
    private List<Catch> fishCatchList;


    public CatchListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catch_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewCatch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        catchAdapter = new CatchAdapter(requireContext(), fishCatchList);
        recyclerView.setAdapter(catchAdapter);

        NavController navController = NavHostFragment.findNavController(this);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(requireContext(), recyclerView,
                (view1, position) -> {
                    if (position >= 0 && position < catchAdapter.getItemCount()) {
                        Catch selectedCatch = catchAdapter.getItem(position);

                        Bundle bundle = new Bundle();

                        if (selectedCatch != null) {

                            bundle.putInt("catchid", selectedCatch.getCatchId());
                            bundle.putString("location", selectedCatch.getLocation());
                            bundle.putString("weight", selectedCatch.getWeight());
                            bundle.putString("size", selectedCatch.getSize());
                            bundle.putString("specie", selectedCatch.getSpecies());
                            bundle.putString("description", selectedCatch.getDescription());
                            bundle.putString("imageUri", selectedCatch.getImageUri());

                            }

                            Navigation.findNavController(view1).navigate(R.id.action_catchListFragment_to_catchDetailFragment, bundle);

                        } else {
                            Log.e("HomeFragment", "Selected fish is null");
                        }
                }));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fishCatchList = loadFishCatchesFromDatabase();

        catchAdapter = new CatchAdapter(requireContext(), fishCatchList);

        recyclerView.setAdapter(catchAdapter);

    }

    private List<Catch> loadFishCatchesFromDatabase() {
        List<Catch> catches = new ArrayList<>();

        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] projection = {
                    DatabaseHelper.KEY_CATCH_ID,
                    DatabaseHelper.KEY_LOCATION,
                    DatabaseHelper.KEY_WEIGHT,
                    DatabaseHelper.KEY_SIZE,
                    DatabaseHelper.KEY_SPECIE,
                    DatabaseHelper.KEY_DESCRIPTION,
                    DatabaseHelper.KEY_IMAGE_URI
            };

            int userId = getCurrentUserId();
            String selection = DatabaseHelper.KEY_USER_ID_FK + " = ?";
            String[] selectionArgs = { String.valueOf(userId) };

            cursor = db.query(
                    DatabaseHelper.TABLE_CATCHES,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            while (cursor != null && cursor.moveToNext()) {
                int catchId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_CATCH_ID));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_LOCATION));
                String weight = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_WEIGHT));
                String size = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SIZE));
                String specie = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SPECIE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_DESCRIPTION));
                String imageUri = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_IMAGE_URI));

                // Új FishCatch objektum létrehozása és hozzáadása a listához
                Catch fishCatch = new Catch(location, weight, size, specie, description, imageUri, catchId);
                catches.add(fishCatch);
            }
        } catch (Exception e) {
            // Hiba kezelése
            Log.e("Database Error", "Error querying catches from DB", e);
        } finally {
            // Cursor és adatbázis lezárása
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return catches;
    }

    private int getCurrentUserId() {
        SessionManager sessionManager = new SessionManager(getActivity());
        return sessionManager.getUserId();
    }

}
