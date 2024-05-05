package com.example.fishapi;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Calendar;


public class SignupFragment extends Fragment {
    private EditText firstNameInput, lastNameInput, emailInput, pwdInput, dateOfBirthInput;
    private CheckBox termsCheckBox;
    private Button signUpSubmitBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        firstNameInput = view.findViewById(R.id.firstNameInput);
        lastNameInput = view.findViewById(R.id.lastNameInput);
        emailInput = view.findViewById(R.id.emailInput);
        pwdInput = view.findViewById(R.id.pwdInput);
        dateOfBirthInput = view.findViewById(R.id.dateOfBirthInput);
        termsCheckBox = view.findViewById(R.id.checkBox);
        signUpSubmitBtn = view.findViewById(R.id.signInBtn);

        dateOfBirthInput.setOnClickListener(v -> showDatePickerDialog());

        signUpSubmitBtn.setOnClickListener(v -> {
            if (validateForm()){
                registerUser();
            }
        });
        return view;
    }

    private void showDatePickerDialog(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = year1 + "-" + (month1 +1) + "-" + dayOfMonth;
                    dateOfBirthInput.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private boolean validateForm(){
        //Chcek if there are any empty fields:
        if (TextUtils.isEmpty(firstNameInput.getText())||
                TextUtils.isEmpty(lastNameInput.getText())||
                TextUtils.isEmpty(emailInput.getText())||
                TextUtils.isEmpty(pwdInput.getText())||
                TextUtils.isEmpty(dateOfBirthInput.getText())){
            Toast.makeText(getActivity(), "All fields must be filled!", Toast.LENGTH_LONG).show();
            return false;
        }

        //first name validate
        String firstName = firstNameInput.getText().toString();
        if (!firstName.matches("^[A-Z][a-z]*")) {
            firstNameInput.setError("First name must start with an uppercase letter!");
        }

        //last name validate
        String lastName = lastNameInput.getText().toString();
        if (!lastName.matches("^[A-Z][a-z]*")) {
            lastNameInput.setError("Last name must start with an uppercase letter!");
        }

        //Validate the email field
        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput.getText()).matches()){
            emailInput.setError("Invalid email");
            return false;
        }

        //pwd validation: at least 8 chars long, one upper one lower one number
        String password = pwdInput.getText().toString();
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";

        if (!password.matches(passwordPattern)){
            pwdInput.setError("Password must be at least 8 characters long, must contain one uppercase one lowercase and one number!");
            return false;
        }

        //check if terms are accepted
        if (!termsCheckBox.isChecked()){
            Toast.makeText(getActivity(), "You must accept the terms of service!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void clearInputFields() {
        firstNameInput.setText("");
        lastNameInput.setText("");
        emailInput.setText("");
        pwdInput.setText("");
        dateOfBirthInput.setText("");
        termsCheckBox.setChecked(false);
    }

    private void registerUser(){
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_USER_FIRST_NAME, firstNameInput.getText().toString());
        values.put(DatabaseHelper.KEY_USER_LAST_NAME, lastNameInput.getText().toString());
        values.put(DatabaseHelper.KEY_USER_EMAIL, emailInput.getText().toString());
        values.put(DatabaseHelper.KEY_USER_PASSWORD, pwdInput.getText().toString());
        values.put(DatabaseHelper.KEY_USER_DOB, dateOfBirthInput.getText().toString());

        long userId = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        db.close();
        if(userId > 0){
            Toast.makeText(getActivity(), "Registration Successful!", Toast.LENGTH_LONG).show();

            clearInputFields();
        } else {
            Toast.makeText(getActivity(), "Registration Failed!", Toast.LENGTH_LONG).show();
        }
    }
}