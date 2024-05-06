package com.example.fishapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class MainActivity extends AppCompatActivity {

    private NavController navController;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);


        navController = Navigation.findNavController(this, R.id.fragmentContainerView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        boolean isLoggedIn = getLoginState();

        Log.d("MenuSetup", "Login state: " + isLoggedIn);

        MenuItem loginItem = menu.findItem(R.id.action_login);
        MenuItem signupItem = menu.findItem(R.id.action_signup);
        MenuItem logoutItem = menu.findItem(R.id.action_logout);

        loginItem.setVisible(!isLoggedIn);
        signupItem.setVisible(!isLoggedIn);
        logoutItem.setVisible(isLoggedIn);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home){
            setTitle("Home");
            navController.navigate(R.id.homeFragment);
            return true;
        } else if (id == R.id.action_login) {
            setTitle("Sign In");
            navController.navigate(R.id.loginFragment);
            return true;
        } else if (id == R.id.action_signup) {
            setTitle("Sign Up");
            navController.navigate(R.id.signupFragment);
            return true;
        } else if (id == R.id.action_logout) {
            setLoginState(false);
            invalidateOptionsMenu();
            setTitle("Sign In");
            navController.navigate(R.id.loginFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setLoginState(boolean isLoggedIn){
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("IsLoggedIn", isLoggedIn);
        editor.apply();
        invalidateOptionsMenu();


        boolean check = prefs.getBoolean("IsLoggedIn", false);
        Log.d("CheckLoginState", "Is logged in set to: " + isLoggedIn + " and read as: " + check);
    }

    public boolean getLoginState(){
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        return prefs.getBoolean("IsLoggedIn", false);
    }


}
