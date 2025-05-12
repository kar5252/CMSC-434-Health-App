package com.example.test4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        ListView workoutListView = view.findViewById(R.id.workoutListView);
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
