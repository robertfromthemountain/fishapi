package com.example.fishapi.recordCatch;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fishapi.signUp.DatabaseHelper;
import com.example.fishapi.R;
import com.example.fishapi.login.SessionManager;

public class RecordCatchFragment extends Fragment {

    private static final int IMAGE_PICK_CODE = 1000;

    private ImageView imageView;
    private EditText locationInput, weightInput, sizeInput, specieInput, descriptionInput;
    private Button saveBtn;
    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_catch, container, false);

        imageView = view.findViewById(R.id.recordCatchImageView);
        locationInput = view.findViewById(R.id.locationInput);
        weightInput = view.findViewById(R.id.weightInput);
        sizeInput = view.findViewById(R.id.sizeInput);
        specieInput = view.findViewById(R.id.specieInput);
        descriptionInput = view.findViewById(R.id.descriptionInput);
        saveBtn = view.findViewById(R.id.saveBtn);

        imageView.setOnClickListener(v -> openGallery());
        saveBtn.setOnClickListener(v -> {
            int userId = getCurrentUserId();
            saveCatch(userId,
                    locationInput.getText().toString(),
                    weightInput.getText().toString(),
                    sizeInput.getText().toString(),
                    specieInput.getText().toString(),
                    descriptionInput.getText().toString());
        });

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }
    private void clearInputFields() {
        imageView.setImageBitmap(null);
        locationInput.setText("");
        weightInput.setText("");
        sizeInput.setText("");
        specieInput.setText("");
        descriptionInput.setText("");
    }
    private void saveCatch(int userId, String location, String weight, String size, String specie, String description) {
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_USER_ID_FK, userId);
        values.put(DatabaseHelper.KEY_LOCATION, location);
        values.put(DatabaseHelper.KEY_WEIGHT, weight);
        values.put(DatabaseHelper.KEY_SIZE, size);
        values.put(DatabaseHelper.KEY_SPECIE, specie);
        values.put(DatabaseHelper.KEY_DESCRIPTION, description);
        if (imageUri != null) {
            values.put(DatabaseHelper.KEY_IMAGE_URI, imageUri.toString());
        }

        try {
            long id = db.insertOrThrow(DatabaseHelper.TABLE_CATCHES, null, values);
            Toast.makeText(getActivity(), "Catch saved!", Toast.LENGTH_SHORT).show();
            clearInputFields();
        } catch (SQLException e) {
            Log.e("Database Error", "Error inserting into DB", e);
            Toast.makeText(getActivity(), "Failed to save catch: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
    private int getCurrentUserId() {
        SessionManager sessionManager = new SessionManager(getActivity());
        return sessionManager.getUserId();
    }
}
