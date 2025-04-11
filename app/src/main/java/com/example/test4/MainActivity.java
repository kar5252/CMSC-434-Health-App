package com.example.test4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements TabNavigator {
    TabLayout tablayout;
    ViewPager2 viewPager2;
//    These variables are for making the tabs appear larger:
    //TabLayout tabLayout = findViewById(R.id.tabLayout);
    String [] tabTitles = {"Home", "Calorie Tracker", "Workout", "Goal"};

    ViewPagerAdapter viewPagerAdapter;
    //everything below is for the functionality of the tabs
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //EdgeToEdge.enable(this);
        tablayout = findViewById(R.id.tabLayout);
        //tablayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tablayout.getTabAt(position).select();
            }
        });

        //this for loop below is for the tabs:
        for(int i=0; i < tablayout.getTabCount(); i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);
            if(tab != null) {
                View customView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
                TextView textView = customView.findViewById(R.id.tabText);
                textView.setText(tabTitles[i]);
                tab.setCustomView(customView);
            }
        }
    }
    @Override
    public void workoutTab() {
        viewPager2.setCurrentItem(2);
    }
    @Override
    public void calorieTab(){
        viewPager2.setCurrentItem(1);
    }
}