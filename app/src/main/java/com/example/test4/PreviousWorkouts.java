package com.example.test4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PreviousWorkouts extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_previous_workouts, container, false);

        ListView historyListView = view.findViewById(R.id.historyListView);

        WorkoutAdapter adapter = new WorkoutAdapter(requireContext(), Workout.getWorkoutHistory());
        historyListView.setAdapter(adapter);

        view.findViewById(R.id.backButton).setOnClickListener(v -> {
            FrameLayout fragmentContainer = requireActivity().findViewById(R.id.fragmentContainer);
            fragmentContainer.setVisibility(View.GONE);
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}
