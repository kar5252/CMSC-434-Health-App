package com.example.test4;

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

import java.util.ArrayList;

public class JournalFragment extends Fragment {

    private EditText goalInput;
    private Button addGoalButton;
    private ListView goalsListView;

    private ArrayAdapter<String> adapter;
    private final ArrayList<String> goalsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        goalInput = view.findViewById(R.id.goalInput);
        addGoalButton = view.findViewById(R.id.addGoalButton);
        goalsListView = view.findViewById(R.id.goalsListView);

        adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_multiple_choice,
                goalsList
        );

        goalsListView.setAdapter(adapter);
        goalsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGoal = goalInput.getText().toString().trim();
                if (!newGoal.isEmpty()) {
                    goalsList.add(newGoal);
                    adapter.notifyDataSetChanged();
                    goalInput.setText("");
                }
            }
        });

        return view;
    }
}
