package com.example.test4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Workout extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        Spinner muscleDropdown = view.findViewById(R.id.muscleDropdown);
        Spinner durationDropdown = view.findViewById(R.id.durationDropdown);
        ListView workoutListView = view.findViewById(R.id.workoutListView);
        Button confirmWorkoutButton = view.findViewById(R.id.confirmWorkoutButton);

        Map<String, List<String>> workoutData = new HashMap<>();
        workoutData.put("chest", new ArrayList<String>() {{
            add("Push-ups (Easy)");
            add("Bench Press (Medium)");
            add("Incline Dumbbell Press (Hard)");
        }});
        workoutData.put("legs", new ArrayList<String>() {{
            add("Bodyweight Squats (Easy)");
            add("Lunges (Medium)");
            add("Barbell Squats (Hard)");
        }});
        workoutData.put("back", new ArrayList<String>() {{
            add("Pull-ups (Easy)");
            add("Lat Pulldown (Medium)");
            add("Deadlift (Hard)");
        }});

        List<String> muscleGroups = new ArrayList<>(workoutData.keySet());
        ArrayAdapter<String> muscleAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, muscleGroups);
        muscleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        muscleDropdown.setAdapter(muscleAdapter);

        List<String> durations = new ArrayList<>();
        durations.add("15 minutes");
        durations.add("30 minutes");
        durations.add("45 minutes");
        durations.add("60 minutes");

        ArrayAdapter<String> durationAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, durations);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationDropdown.setAdapter(durationAdapter);

        muscleDropdown.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedMuscle = muscleGroups.get(position);
                List<String> recommendations = workoutData.getOrDefault(selectedMuscle, new ArrayList<>());
                CustomAdapter adapter = new CustomAdapter(recommendations);
                workoutListView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                workoutListView.setAdapter(null);
            }
        });

        confirmWorkoutButton.setOnClickListener(v -> {
            String selectedMuscle = muscleDropdown.getSelectedItem().toString();
            String selectedDuration = durationDropdown.getSelectedItem().toString();

            Toast.makeText(getContext(), "Workout Confirmed:\n" +
                    "Muscle: " + selectedMuscle + "\nDuration: " + selectedDuration, Toast.LENGTH_LONG).show();
        });

        return view;
    }

    private class CustomAdapter extends ArrayAdapter<String> {
        private final List<String> workouts;

        public CustomAdapter(@NonNull List<String> workouts) {
            super(requireContext(), R.layout.list_item_workout, workouts);
            this.workouts = workouts;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_workout, parent, false);
            }

            TextView workoutItemText = convertView.findViewById(R.id.workoutItemText);
            String workout = workouts.get(position);
            workoutItemText.setText(workout);

            if (workout.contains("(Easy)")) {
                workoutItemText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else if (workout.contains("(Medium)")) {
                workoutItemText.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            } else if (workout.contains("(Hard)")) {
                workoutItemText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }

            return convertView;
        }
    }
}
