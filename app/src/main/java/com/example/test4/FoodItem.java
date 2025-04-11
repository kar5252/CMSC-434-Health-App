package com.example.test4;

//Lets user: Search from predefined food list, Add a custom food item (name + calories)
//Passes selected item back to AddMealActivity
public class FoodItem {
    public String name;
    public int calories;
    public String emoji;

    public FoodItem(String name, int calories, String emoji) {
        this.name = name;
        this.calories = calories;
        this.emoji = emoji;
    }
}
