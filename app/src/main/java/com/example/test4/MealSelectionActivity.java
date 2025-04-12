package com.example.test4;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

//meal picker shows Breakfast, Lunch, Dinner, Snack as buttons
//and handles On button press -> launches AddMealActivity with mealType in Intent
public class MealSelectionActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_MEAL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_selection);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Back");

        findViewById(R.id.btnBreakfast).setOnClickListener(v -> startMeal("Breakfast"));
        findViewById(R.id.btnLunch).setOnClickListener(v -> startMeal("Lunch"));
        findViewById(R.id.btnDinner).setOnClickListener(v -> startMeal("Dinner"));
        findViewById(R.id.btnSnack).setOnClickListener(v -> startMeal("Snack"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startMeal(String mealType) {
        Intent intent = new Intent(this, AddMealActivity.class);
        intent.putExtra("mealType", mealType);
        startActivityForResult(intent, REQUEST_CODE_ADD_MEAL); //use something else, will fix later
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_MEAL && resultCode == RESULT_OK && data != null) {
            // send result back to Calorie_Tracker
            setResult(RESULT_OK, data);
            finish();
        }
    }
}

