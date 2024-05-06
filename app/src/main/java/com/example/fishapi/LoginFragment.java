package com.example.fishapi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class LoginFragment extends Fragment {
    private EditText emailInput, pwdInput;
    private Button signInButton;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailInput = view.findViewById(R.id.emailInput);
        pwdInput = view.findViewById(R.id.pwdInput);
        signInButton = view.findViewById(R.id.signInBtn);
        sessionManager = new SessionManager(getActivity());

        signInButton.setOnClickListener(v -> handleLogin());
        return view;
    }

    private void handleLogin() {
        String email = emailInput.getText().toString().trim();
        String password = pwdInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Email and password must not be empty!", Toast.LENGTH_LONG).show();
            return;
        }

        if (loginUser(email, password)) {
            int userId = fetchUserId(email);
            if (userId != -1) {
                sessionManager.setLogin(true, userId);
                ((MainActivity) getActivity()).setLoginState(true, userId);
                Toast.makeText(getActivity(), "Login Successful!", Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_loginFragment_to_homeFragment);
            } else {
                Toast.makeText(getActivity(), "Failed to retrieve user ID.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Invalid email or password!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean loginUser(String email, String password) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.KEY_USER_ID};
        String selection = DatabaseHelper.KEY_USER_EMAIL + "=? AND " + DatabaseHelper.KEY_USER_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        boolean loginSuccess = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return loginSuccess;
    }

    private int fetchUserId(String email) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int userId = -1;

        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS,
                new String[]{DatabaseHelper.KEY_USER_ID},
                DatabaseHelper.KEY_USER_EMAIL + "=?",
                new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DatabaseHelper.KEY_USER_ID);
            if (columnIndex != -1) {
                userId = cursor.getInt(columnIndex);
            } else {
                Toast.makeText(getContext(), "Failed to retrieve user ID.", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } else {
            Toast.makeText(getContext(), "No account found with that email.", Toast.LENGTH_SHORT).show();
        }

        db.close();
        return userId;
    }
}
