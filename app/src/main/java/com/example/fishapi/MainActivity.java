package com.example.fishapi;

import android.content.SharedPreferences;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    String fileName = "fishAPIResult.txt";

    Context context = this;

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

        /*File file = new File(context.getFilesDir(), fileName);
        final JsonArray[] FishArray = new JsonArray[1];


        if (file.exists()) {

            FishArray[0] = readFromFile(file);

        } else {*/

        FishApiService service = FishApiClient.getClient().create(FishApiService.class);

        Call<JsonArray> call = service.getFishData();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    JsonArray fishArray = response.body();

                    String jsonFishString = fishArray.toString();

                    try {
                        File file = new File(context.getExternalFilesDir(null), fileName);
                        FileWriter writer = new FileWriter(file);
                        writer.append(jsonFishString);
                        writer.flush();
                        writer.close();
                        Log.d("Fájlba írás: ", "Fájlba írás sikeres");
                    } catch (IOException e) {
                        Log.d("Fájlba írás: ", "Fájlba írás sikertelen");
                        e.printStackTrace();
                    }

                    if (fishArray != null && !fishArray.isJsonNull() && fishArray.size() > 0) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Fish>>() {
                        }.getType();
                        List<Fish> fishList = gson.fromJson(fishArray, type);

                        RecyclerView recyclerView = findViewById(R.id.recycleView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        FishAdapter fishAdapter = new FishAdapter();
                        recyclerView.setAdapter(fishAdapter);

                        if (fishList != null && !fishList.isEmpty()) {
                            /*for (Fish fish : fishList) {
                                Log.d("FishName", "Fish name: " + fish.getName());
                            }*/
                            //Log.d("FishListSize", "Fish list size: " + fishList.size());

                            fishAdapter.setFishList(fishList);
                            //fishAdapter.notifyDataSetChanged();


                        } else {
                            Log.e("FishList", "Fish list is null or empty");
                        }

                    } else {
                        // A válasz üres vagy null
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
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

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
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
    public static JsonArray readFromFile(File file) {
        JsonArray jsonArray = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            // Szöveges JSON átalakítása JsonArray objektummá
            String jsonString = stringBuilder.toString();
            Gson gson = new Gson();
            jsonArray = gson.fromJson(jsonString, JsonArray.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

}
