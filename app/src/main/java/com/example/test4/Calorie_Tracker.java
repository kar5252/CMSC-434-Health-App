package com.example.test4;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Calorie_Tracker extends Fragment {

    private ActivityResultLauncher<Intent> addMealLauncher;

    private int totalCaloriesSoFar = 0;
    private final int dailyGoal = 2000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calorie__tracker, container, false);

        // Setup launcher to receive data from AddMealActivity
        addMealLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        int mealCalories = data.getIntExtra("mealCalories", 0);
                        String mealType = data.getStringExtra("mealType");

                        ArrayList<FoodItem> foodList = data.getParcelableArrayListExtra("foodItems");

                        updateCalorieProgress(mealCalories);

                        if (foodList != null && mealType != null) {
                            updateMealSection(view, mealType, foodList);
                        }
                    }
                }
        );

        // Set up individual meal add buttons
        Button breakfastAdd = view.findViewById(R.id.breakfastAddButton);
        Button lunchAdd = view.findViewById(R.id.lunchAddButton);
        Button dinnerAdd = view.findViewById(R.id.dinnerAddButton);
        Button snackAdd = view.findViewById(R.id.snackAddButton);

        breakfastAdd.setOnClickListener(v -> openAddMealScreen("Breakfast"));
        lunchAdd.setOnClickListener(v -> openAddMealScreen("Lunch"));
        dinnerAdd.setOnClickListener(v -> openAddMealScreen("Dinner"));
        snackAdd.setOnClickListener(v -> openAddMealScreen("Snack"));

        return view;
    }

    private void openAddMealScreen(String mealType) {
        Intent intent = new Intent(getActivity(), AddMealActivity.class);
        intent.putExtra("mealType", mealType); // Pass selected meal type directly
        addMealLauncher.launch(intent);
    }

    private void updateCalorieProgress(int addedCalories) {
        totalCaloriesSoFar += addedCalories;

        ProgressBar progressBar = getView().findViewById(R.id.progressBar);
        TextView calorieLabel = getView().findViewById(R.id.calorieLabel);

        progressBar.setMax(dailyGoal);
        progressBar.setProgress(totalCaloriesSoFar);

        calorieLabel.setText(totalCaloriesSoFar + "/" + dailyGoal + " Calories");
    }

    private void updateMealSection(View rootView, String mealType, ArrayList<FoodItem> foodItems) {
        int targetLayoutId;

        switch (mealType) {
            case "Breakfast":
                targetLayoutId = R.id.breakfastSection;
                break;
            case "Lunch":
                targetLayoutId = R.id.lunchSection;
                break;
            case "Dinner":
                targetLayoutId = R.id.dinnerSection;
                break;
            case "Snack":
                targetLayoutId = R.id.snackSection;
                break;
            default:
                return;
        }

        LinearLayout mealLayout = rootView.findViewById(targetLayoutId);

        for (FoodItem item : foodItems) {
            TextView itemView = new TextView(getContext());
            itemView.setText(item.emoji + " " + item.name + " - " + item.calories + " cal");
            itemView.setTextSize(18);
            itemView.setPadding(12, 4, 12, 4);
            itemView.setTextColor(Color.DKGRAY);

            mealLayout.addView(itemView);
        }
    }
}

