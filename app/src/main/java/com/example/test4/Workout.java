package com.example.test4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class Workout extends Fragment {

    private static final List<String> workoutHistory = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        ListView workoutListView = view.findViewById(R.id.workoutListView);
        final List<String> recommendations = new ArrayList<>();
        recommendations.add("Push-ups (Easy)");
        recommendations.add("Bench Press (Medium)");
        recommendations.add("Incline Dumbbell Press (Hard)");

        final ArrayAdapter<String> recAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, recommendations);
        workoutListView.setAdapter(recAdapter);

        Button muscleChest = view.findViewById(R.id.muscleChest);
        Button muscleLegs = view.findViewById(R.id.muscleLegs);
        Button muscleBack = view.findViewById(R.id.muscleBack);
        Button muscleShoulders = view.findViewById(R.id.muscleShoulders);
        Button muscleArms = view.findViewById(R.id.muscleArms);
        Button muscleCore = view.findViewById(R.id.muscleCore);

        muscleChest.setOnClickListener(v -> {
            recommendations.clear();
            recommendations.add("Push-ups (Easy)");
            recommendations.add("Bench Press (Medium)");
            recommendations.add("Incline Dumbbell Press (Hard)");
            recAdapter.notifyDataSetChanged();
        });

        muscleLegs.setOnClickListener(v -> {
            recommendations.clear();
            recommendations.add("Bodyweight Squats (Easy)");
            recommendations.add("Lunges (Medium)");
            recommendations.add("Barbell Squats (Hard)");
            recAdapter.notifyDataSetChanged();
        });

        muscleBack.setOnClickListener(v -> {
            recommendations.clear();
            recommendations.add("Pull-ups (Easy)");
            recommendations.add("Lat Pulldown (Medium)");
            recommendations.add("Deadlift (Hard)");
            recAdapter.notifyDataSetChanged();
        });

        muscleShoulders.setOnClickListener(v -> {
            recommendations.clear();
            recommendations.add("Dumbbell Shoulder Press (Easy)");
            recommendations.add("Lateral Raises (Medium)");
            recommendations.add("Barbell Overhead Press (Hard)");
            recAdapter.notifyDataSetChanged();
        });

        muscleArms.setOnClickListener(v -> {
            recommendations.clear();
            recommendations.add("Bicep Curls (Easy)");
            recommendations.add("Tricep Dips (Medium)");
            recommendations.add("Hammer Curls (Hard)");
            recAdapter.notifyDataSetChanged();
        });

        muscleCore.setOnClickListener(v -> {
            recommendations.clear();
            recommendations.add("Planks (Easy)");
            recommendations.add("Russian Twists (Medium)");
            recommendations.add("Sit-ups (Hard)");
            recAdapter.notifyDataSetChanged();
        });

        EditText exerciseNameInput = view.findViewById(R.id.exerciseNameInput);
        EditText repsInput = view.findViewById(R.id.repsInput);
        EditText setsInput = view.findViewById(R.id.setsInput);
        EditText durationInput = view.findViewById(R.id.durationInput);
        Button confirmWorkoutButton = view.findViewById(R.id.confirmWorkoutButton);
        Button previousWorkoutsButton = view.findViewById(R.id.previousWorkoutsButton);

        confirmWorkoutButton.setOnClickListener(v -> {
            String workoutDetails = "Exercise: " + exerciseNameInput.getText().toString() +
                    ", Reps: " + repsInput.getText().toString() +
                    ", Sets: " + setsInput.getText().toString() +
                    ", Duration: " + durationInput.getText().toString() + " min";
            workoutHistory.add(workoutDetails);
            Toast.makeText(requireContext(), "Workout saved!", Toast.LENGTH_SHORT).show();
        });

        previousWorkoutsButton.setOnClickListener(v -> {
            View containerView = requireActivity().findViewById(R.id.fragmentContainer);
            containerView.setVisibility(View.VISIBLE);
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, new PreviousWorkouts());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    public static List<String> getWorkoutHistory() {
        return workoutHistory;
    }
}
