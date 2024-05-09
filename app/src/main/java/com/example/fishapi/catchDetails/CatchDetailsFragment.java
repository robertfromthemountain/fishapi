package com.example.fishapi.catchDetails;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fishapi.DatabaseHelper;
import com.example.fishapi.R;
import com.example.fishapi.dtos.ImgSrcSet;
import com.google.gson.Gson;

public class CatchDetailsFragment extends Fragment {

    private Button backButton, deleteButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catch_details, container, false);

        TextView locationTextView = view.findViewById(R.id.locationTextView);
        TextView weightTextView = view.findViewById(R.id.weightTextView);
        TextView sizeTextView = view.findViewById(R.id.sizeTextView);
        TextView specieTextView = view.findViewById(R.id.specieTextView);
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        ImageView catchImageView = view.findViewById(R.id.catchImageView);
        backButton = view.findViewById(R.id.buttonBackToList);
        deleteButton = view.findViewById(R.id.buttonDeleteCatch);

        Bundle bundle = getArguments();
        if (bundle != null) {

            String location = bundle.getString("location", "N/A");
            String weight = bundle.getString("weight", "N/A");
            String size = bundle.getString("size", "N/A");
            String specie = bundle.getString("specie", "N/A");
            String description = bundle.getString("description", "N/A");
            String imageUri = bundle.getString("imageUri");


            locationTextView.setText("Location: " + location);
            weightTextView.setText("Weight: " + weight);
            sizeTextView.setText("Size: " + size);
            specieTextView.setText("Species: " + specie);
            descriptionTextView.setText("Description: " + description);


            if (imageUri != null) {
                Uri catchImage = Uri.parse(imageUri);
                    Glide.with(requireContext())
                            .load(catchImage)
                            .placeholder(R.drawable.placeholder_image)
                            .centerCrop()
                            .into(catchImageView);
                } else {
                catchImageView.setImageResource(R.drawable.placeholder_image);
                }
            }

        backButton.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        deleteButton.setOnClickListener(v -> {
            int catchId = bundle.getInt("catchid", -1);
            Log.d("Delete", "delete1");
            if (catchId != -1) {
                Log.d("Delete", "delete2");
                deleteCatch(catchId);
            }
        });

        return view;
    }

    private void deleteCatch(int catchId) {
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            String selection = DatabaseHelper.KEY_CATCH_ID + " = ?";
            String[] selectionArgs = { String.valueOf(catchId) };
            db.delete(DatabaseHelper.TABLE_CATCHES, selection, selectionArgs);
            Toast.makeText(requireContext(), "Catch record deleted", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Delete Error", "Error deleting fish catch from DB", e);
            Toast.makeText(requireContext(), "Failed to delete catch record", Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
            getParentFragmentManager().popBackStack();
        }
    }
}
