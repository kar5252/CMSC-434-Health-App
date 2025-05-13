package com.example.test4;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import com.example.test4.FoodItemAdapter;
import com.example.test4.FoodItem;

public class AddMealActivity extends AppCompatActivity {

    private TextView tvMealTitle, tvTotalCalories;
    private ListView listViewFoodItems;

    private ArrayList<FoodItem> foodList;
    private FoodItemAdapter foodAdapter;
    private String mealType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Back");

        tvMealTitle = findViewById(R.id.tvMealTitle);
        tvTotalCalories = findViewById(R.id.tvTotalCalories);
        listViewFoodItems = findViewById(R.id.listViewFoodItems);
        Button btnAddFoodItem = findViewById(R.id.btnAddFoodItem);
        Button btnConfirmAddMeal = findViewById(R.id.btnConfirmAddMeal);

        // get meal type from previous activity
        mealType = getIntent().getStringExtra("mealType");
        if (mealType == null) mealType = "Meal";

        // SETTINGS FOR THE "ADDING: _____"
        String mealEmoji = getMealEmoji(mealType);
        tvMealTitle.setText("Adding: " + mealType + " " + mealEmoji);
        tvMealTitle.setTextSize(30); // or any size you want, in SP units


        foodList = new ArrayList<>();
        foodAdapter = new FoodItemAdapter(this, foodList, tvTotalCalories);
        listViewFoodItems.setAdapter(foodAdapter);

        btnAddFoodItem.setOnClickListener(view -> showAddFoodDialog());

        btnConfirmAddMeal.setOnClickListener(view -> {
            int total = 0;
            for (FoodItem f : foodList) {
                total += f.calories;
            }
            Intent resultIntent = new Intent();
            resultIntent.putExtra("mealCalories", total);
            resultIntent.putExtra("mealType", mealType);
            resultIntent.putParcelableArrayListExtra("foodItems", foodList);
            setResult(RESULT_OK, resultIntent);

            Toast.makeText(this, "Meal added!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private String getMealEmoji(String mealType) {
        switch (mealType.toLowerCase()) {
            case "breakfast":
                return "🍳";
            case "lunch":
                return "🍱";
            case "dinner":
                return "🍛";
            case "snack":
                return "🥨";
            default:
                return "🍴";
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddFoodDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Food Item");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_food, null);
        builder.setView(dialogView);

        EditText etFoodName = dialogView.findViewById(R.id.etFoodName);
        EditText etCalories = dialogView.findViewById(R.id.etCalories);

        builder.setPositiveButton("Add", (dialogInterface, i) -> {
            String foodName = etFoodName.getText().toString();
            String caloriesStr = etCalories.getText().toString();

            if (!foodName.isEmpty() && !caloriesStr.isEmpty()) {
                int kcal = Integer.parseInt(caloriesStr);

                String emoji = "🍽️";
                if (foodName.toLowerCase().contains("egg")) emoji = "🥚";
                else if (foodName.toLowerCase().contains("toast")) emoji = "🍞";
                else if (foodName.toLowerCase().contains("pancake")) emoji = "🥞";
                else if (foodName.toLowerCase().contains("banana")) emoji = "🍌";
                else if (foodName.toLowerCase().contains("pasta")) emoji = "🍝";
                else if (foodName.toLowerCase().contains("pizza")) emoji = "🍕";
                else if (foodName.toLowerCase().contains("cereal")) emoji = "🥣";
                else if (foodName.toLowerCase().contains("waffle")) emoji = "🧇";
                else if (foodName.toLowerCase().contains("donut")) emoji = "🍩";
                else if (foodName.toLowerCase().contains("bagel")) emoji = "🥯";
                else if (foodName.toLowerCase().contains("steak")) emoji = "🥩";
                else if (foodName.toLowerCase().contains("salad")) emoji = "🥗";
                else if (foodName.toLowerCase().contains("sandwich")) emoji = "🥪";
                else if (foodName.toLowerCase().contains("chicken")) emoji = "🍗";
                else if (foodName.toLowerCase().contains("fish")) emoji = "🐟";
                else if (foodName.toLowerCase().contains("ramen")) emoji = "🍜";
                else if (foodName.toLowerCase().contains("soup")) emoji = "🍲";
                else if (foodName.toLowerCase().contains("apple")) emoji = "🍎";
                else if (foodName.toLowerCase().contains("hotdog")) emoji = "🌭";
                else if (foodName.toLowerCase().contains("popcorn")) emoji = "🍿";
                else if (foodName.toLowerCase().contains("taco")) emoji = "🌮";
                else if (foodName.toLowerCase().contains("burrito")) emoji = "🌯";
                else if (foodName.toLowerCase().contains("fries")) emoji = "🍟";
                else if (foodName.toLowerCase().contains("pie")) emoji = "🥧";

                foodList.add(new FoodItem(foodName, kcal, emoji));
                foodAdapter.notifyDataSetChanged();
                updateTotalCalories();

            } else{
                Toast.makeText(this, "Invalid Input ", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void updateTotalCalories() {
        int total = 0;
        for (FoodItem f : foodList) {
            total += f.calories;
        }
        tvTotalCalories.setText("Total Calories: " + total + " cals");
    }
}



