package com.example.test4;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class JournalFragment extends Fragment {

    private EditText goalInput;
    private Button addGoalButton;

    private Button deleteGoalsButton;

    private ListView goalsListView;

    private ArrayAdapter<String> adapter;
    private final ArrayList<String> goalsList = new ArrayList<>();

    private RadioButton radioDaily;
    private RadioButton radioLongTerm;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        goalInput = view.findViewById(R.id.goalInput);
        addGoalButton = view.findViewById(R.id.addGoalButton);
        goalsListView = view.findViewById(R.id.goalsListView);

        pieChart = view.findViewById(R.id.pieChart);
        updatePieChart();

        radioDaily = view.findViewById(R.id.radioDaily);
        radioLongTerm = view.findViewById(R.id.radioLongTerm);


        adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_multiple_choice,
                goalsList
        );

        goalsListView.setAdapter(adapter);
        deleteGoalsButton = view.findViewById(R.id.deleteGoalsButton);
        deleteGoalsButton.setOnClickListener(v -> {
            for (int i = goalsListView.getCount() - 1; i >= 0; i--) {
                if (goalsListView.isItemChecked(i)) {
                    allGoals.remove(i);
                    goalsList.remove(i);
                }
            }
            adapter.notifyDataSetChanged();
            updatePieChart();
        });
        goalsListView.setOnItemClickListener((parent, view1, position, id) -> {
            Goal goal = allGoals.get(position);
            goal.isCompleted = !goal.isCompleted;
            goalsListView.setItemChecked(position, goal.isCompleted);
            updatePieChart();
        });



        goalsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGoalText = goalInput.getText().toString().trim();
                if (!newGoalText.isEmpty()) {
                    boolean isDaily = radioDaily.isChecked();
                    Goal goal = new Goal(newGoalText, isDaily);
                    // true for daily; make a toggle for long-term if needed
                    allGoals.add(goal);
                    goalsList.add(goal.text + (goal.isDaily ? " (Daily)" : " (Long-Term)"));
                    adapter.notifyDataSetChanged();
                    goalsListView.setItemChecked(allGoals.size() - 1, false);
                    updatePieChart();
                    goalInput.setText("");
                }
            }
        });


        return view;
    }
    private final ArrayList<Goal> allGoals = new ArrayList<>();

    // Create a custom class to track goal type and completion
    private static class Goal {
        String text;
        boolean isCompleted;
        boolean isDaily;

        Goal(String text, boolean isDaily) {
            this.text = text;
            this.isDaily = isDaily;
            this.isCompleted = false;
        }
    }

    private PieChart pieChart;

    private void updatePieChart() {
        int dailyCompleted = 0, dailyPending = 0;
        int longCompleted = 0, longPending = 0;

        for (Goal goal : allGoals) {
            if (goal.isDaily) {
                if (goal.isCompleted) dailyCompleted++;
                else dailyPending++;
            } else {
                if (goal.isCompleted) longCompleted++;
                else longPending++;
            }
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        if (dailyCompleted > 0) entries.add(new PieEntry(dailyCompleted, "Daily ✔"));
        if (dailyPending > 0) entries.add(new PieEntry(dailyPending, "Daily ⏳"));
        if (longCompleted > 0) entries.add(new PieEntry(longCompleted, "Long-Term ✔"));
        if (longPending > 0) entries.add(new PieEntry(longPending, "Long-Term ⏳"));

        PieDataSet dataSet = new PieDataSet(entries, "Goal Progress");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // Refresh chart
    }

}
