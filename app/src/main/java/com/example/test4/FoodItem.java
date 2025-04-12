package com.example.test4;

import android.os.Parcel;
import android.os.Parcelable;

// allows user 2 search from predefined food list, Add a custom food item (name + calories)
// passes selected item back to AddMealActivity
public class FoodItem implements Parcelable {
    public String name;
    public int calories;
    public String emoji;

    public FoodItem(String name, int calories, String emoji) {
        this.name = name;
        this.calories = calories;
        this.emoji = emoji;
    }

    protected FoodItem(Parcel in) {
        name = in.readString();
        calories = in.readInt();
        emoji = in.readString();
    }

    public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(calories);
        parcel.writeString(emoji);
    }
}

