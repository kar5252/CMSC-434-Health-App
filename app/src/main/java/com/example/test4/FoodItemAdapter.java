package com.example.test4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class FoodItemAdapter extends ArrayAdapter<FoodItem> {

    private final Context context;
    private final ArrayList<FoodItem> foodItems;
    private final TextView totalCaloriesText;

    public FoodItemAdapter(Context context, ArrayList<FoodItem> items, TextView totalCaloriesText) {
        super(context, 0, items);
        this.context = context;
        this.foodItems = items;
        this.totalCaloriesText = totalCaloriesText;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        FoodItem item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        }

        TextView tvFoodItem = convertView.findViewById(R.id.tvFoodItem);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);

        tvFoodItem.setText(item.emoji + " " + item.name + "  " + item.calories + " cals");

        btnDelete.setOnClickListener(view -> {
            foodItems.remove(position);
            notifyDataSetChanged();
            updateTotalCalories();
        });

        return convertView;
    }

    private void updateTotalCalories() {
        int total = 0;
        for (FoodItem f : foodItems) {
            total += f.calories;
        }
        totalCaloriesText.setText("Total Calories: " + total + " cals");
    }
}

