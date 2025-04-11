package com.example.test4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class PreviousWorkouts extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_previous_workouts, container, false);

        ListView historyListView = view.findViewById(R.id.historyListView);
        Button backButton = view.findViewById(R.id.backButton);

        if (Workout.getWorkoutHistory().isEmpty()) {
            Workout.getWorkoutHistory().add("No previous workouts found.");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, Workout.getWorkoutHistory());
        historyListView.setAdapter(adapter);

        backButton.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, new Workout());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
