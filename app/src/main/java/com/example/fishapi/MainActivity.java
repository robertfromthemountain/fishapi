package com.example.fishapi;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    public NavController navController() {
        return navController;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //TextView test = findViewById(R.id.test);

        Toolbar mainToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);

        navController = Navigation.findNavController(this, R.id.fragmentContainerView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FishApiService service = FishApiClient.getClient().create(FishApiService.class);

        Call<JsonArray> call = service.getFishData();

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    JsonArray fishArray = response.body();

                    if (fishArray != null && !fishArray.isJsonNull() && fishArray.size() > 0) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Fish>>() {}.getType();
                        List<Fish> fishList = gson.fromJson(fishArray, type);

                        RecyclerView recyclerView = findViewById(R.id.recycleView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        FishAdapter fishAdapter = new FishAdapter();
                        recyclerView.setAdapter(fishAdapter);

                        if (fishList != null && !fishList.isEmpty()) {
                            for (Fish fish : fishList) {
                                Log.d("FishName", "Fish name: " + fish.getName());
                            }
                            fishAdapter.setFishList(fishList);


                        } else {
                            Log.e("FishList", "Fish list is null or empty");
                        }

                    } else {
                        // A válasz üres vagy null
                    }
                } else {
                    // Sikertelen válasz
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                // Hálózati hiba
                Log.e("NetworkError", "Hálózati hiba történt", t);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home)
        {
            setTitle("Home");
            navController.navigate(R.id.homeFragment);
        } else if (id == R.id.action_login)
        {
            setTitle("Sign In");
            navController.navigate(R.id.loginFragment);
        } else if (id == R.id.action_signup)
        {
            setTitle("Sign Up");
            navController.navigate(R.id.signupFragment);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

}
