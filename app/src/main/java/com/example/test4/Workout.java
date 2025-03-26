package com.example.test4;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Workout extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        EditText exerciseTypeInput = view.findViewById(R.id.exerciseTypeInput);
        EditText durationInput = view.findViewById(R.id.durationInput);
        EditText repsSetsInput = view.findViewById(R.id.repsSetsInput);
        EditText targetMuscleInput = view.findViewById(R.id.targetMuscleInput);
        ListView workoutListView = view.findViewById(R.id.workoutListView);

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

        targetMuscleInput.setOnEditorActionListener((v, actionId, event) -> {
            String targetMuscle = targetMuscleInput.getText().toString().toLowerCase();
            List<String> recommendations = workoutData.getOrDefault(targetMuscle, new ArrayList<String>() {{
                add("No workouts available for this muscle.");
            }});

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, recommendations);
            workoutListView.setAdapter(adapter);
            return true;
        });


        return view;
    }
}
