package com.example.fishapi;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

public class LoginFragment extends Fragment {
    private EditText emailInput, pwdInput;
    private Button signInButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailInput = view.findViewById(R.id.emailInput);
        pwdInput = view.findViewById(R.id.pwdInput);
        signInButton = view.findViewById(R.id.signInBtn);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        return view;
    }

    private void handleLogin() {
        String email = emailInput.getText().toString();
        String password = pwdInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(getActivity(), "Email and password must not be empty!", Toast.LENGTH_LONG).show();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //SQL Injection prevention
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS,
                new String[]{DatabaseHelper.KEY_USER_EMAIL, DatabaseHelper.KEY_USER_PASSWORD},
                DatabaseHelper.KEY_USER_EMAIL + "=? AND " + DatabaseHelper.KEY_USER_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        if (cursor != null &&cursor.moveToFirst()){
            Toast.makeText(getActivity(), "Login Successful!", Toast.LENGTH_SHORT).show();
            emailInput.setText("");
            pwdInput.setText("");

            //Navigate to the homepage:
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_loginFragment_to_homeFragment);
        } else {
            Toast.makeText(getActivity(), "Invalid email or password!", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null){
            cursor.close();
        }

        db.close();
    }
}