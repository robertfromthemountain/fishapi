package com.example.fishapi;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
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
        Log.d("Startup", "User logged in state: " + sessionManager.isLoggedIn());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        updateMenuItems(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        updateMenuItems(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    private void updateMenuItems(Menu menu) {
        boolean isLoggedIn = sessionManager.isLoggedIn();

        MenuItem loginItem = menu.findItem(R.id.action_login);
        MenuItem signupItem = menu.findItem(R.id.action_signup);
        MenuItem logoutItem = menu.findItem(R.id.action_logout);
        MenuItem recordItem = menu.findItem(R.id.action_record);

        loginItem.setVisible(!isLoggedIn);
        signupItem.setVisible(!isLoggedIn);
        logoutItem.setVisible(isLoggedIn);
        recordItem.setVisible(isLoggedIn);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            setTitle("Home");
            navController.navigate(R.id.homeFragment);
        } else if (id == R.id.action_login) {
            setTitle("Sign In");
            navController.navigate(R.id.loginFragment);
        } else if (id == R.id.action_signup) {
            setTitle("Sign Up");
            navController.navigate(R.id.signupFragment);
        } else if (id == R.id.action_logout) {
            logoutUser();
        } else if (id == R.id.action_record) {
            setTitle("Record Catch");
            navController.navigate(R.id.recordCatchFragment);
        }
        return true;
    }


    public void logoutUser() {
        setLoginState(false, -1);
        invalidateOptionsMenu();
        setTitle("Sign In");
        navController.navigate(R.id.loginFragment);
    }

    public void setLoginState(boolean isLoggedIn, int userId) {
        sessionManager.setLogin(isLoggedIn, userId);
        invalidateOptionsMenu();
    }
}
