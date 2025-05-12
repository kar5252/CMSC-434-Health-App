package com.example.test4;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class WorkoutAdapter extends ArrayAdapter<String> {

    private List<String> workouts;
    private Context context;

    public WorkoutAdapter(Context context, List<String> workouts) {
        super(context, R.layout.item_workout, workouts);
        this.context = context;
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_workout, parent, false);
        }

        TextView workoutText = convertView.findViewById(R.id.workoutText);
        Button editButton = convertView.findViewById(R.id.editButton);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        String currentWorkout = workouts.get(position);
        workoutText.setText(currentWorkout);

        editButton.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_workout, null);
            EditText exerciseInput = dialogView.findViewById(R.id.dialogExerciseNameInput);
            EditText repsInput = dialogView.findViewById(R.id.dialogRepsInput);
            EditText setsInput = dialogView.findViewById(R.id.dialogSetsInput);
            EditText durationInput = dialogView.findViewById(R.id.dialogDurationInput);

            String exercise = "", reps = "", sets = "", duration = "";
            try {
                String[] parts = currentWorkout.split(",");
                if(parts.length >= 4){
                    exercise = parts[0].substring(parts[0].indexOf(":") + 2).trim();
                    reps = parts[1].substring(parts[1].indexOf(":") + 2).trim();
                    sets = parts[2].substring(parts[2].indexOf(":") + 2).trim();
                    String durationPart = parts[3].substring(parts[3].indexOf(":") + 2).trim();
                    duration = durationPart.replace(" min", "");
                }
            } catch (Exception e) {
            }

            exerciseInput.setText(exercise);
            repsInput.setText(reps);
            setsInput.setText(sets);
            durationInput.setText(duration);

            new AlertDialog.Builder(context)
                    .setTitle("Edit Workout")
                    .setView(dialogView)
                    .setPositiveButton("Save", (dialog, which) -> {
                        // Create updated workout string from new data
                        String updatedWorkout = "Exercise: " + exerciseInput.getText().toString() +
                                ", Reps: " + repsInput.getText().toString() +
                                ", Sets: " + setsInput.getText().toString() +
                                ", Duration: " + durationInput.getText().toString() + " min";
                        workouts.set(position, updatedWorkout);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        deleteButton.setOnClickListener(v ->
                new AlertDialog.Builder(context)
                        .setTitle("Delete Workout")
                        .setMessage("Are you sure you want to delete this workout?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            workouts.remove(position);
                            notifyDataSetChanged();
                        })
                        .setNegativeButton("Cancel", null)
                        .show()
        );

        return convertView;
    }
}
