package com.example.test4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Calorie_Tracker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Calorie_Tracker extends Fragment {

    private int CurrentProgress = 0;
    private ProgressBar progressBar;
    private Button startProgress;
    private TextView calorieLabel;
    TextView motivationMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calorie__tracker, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        startProgress = view.findViewById(R.id.start_progress);
        calorieLabel = view.findViewById(R.id.calorieLabel);
        motivationMessage = view.findViewById(R.id.motivationMessage);

        int goalCalories = 2000; // example goal
        int currentCalories = 1600; // example curr progress

        // set progress (0-100 scale)
        progressBar.setProgress((int) ((currentCalories / (float) goalCalories) * 100));

        // update label
        calorieLabel.setText(currentCalories + "/" + goalCalories + " Calories");

        if (currentCalories >= goalCalories * 0.9) {
            motivationMessage.setVisibility(View.VISIBLE);
            motivationMessage.setText("Almost there! 🎯\nYou're crushing it!");
        } else if (currentCalories >= goalCalories * 0.7) {
            motivationMessage.setVisibility(View.VISIBLE);
            motivationMessage.setText("Keep going! 💪\nYou're getting close!");
        } else {
            motivationMessage.setVisibility(View.GONE); // Hide if not near goal
        }

        startProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentProgress = CurrentProgress + 10;
                progressBar.setProgress(CurrentProgress);
                progressBar.setMax(100);
            }
        });

        return view;
    }
}