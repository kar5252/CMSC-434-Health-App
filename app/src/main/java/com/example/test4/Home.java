package com.example.test4;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**

 * create an instance of this fragment.
 */
public class Home extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView editIcon = view.findViewById(R.id.edit_profile);
        TextView usernameTextView = view.findViewById(R.id.username);

        SharedPreferences prefs = requireContext().getSharedPreferences("userProfile", Context.MODE_PRIVATE);
        String firstName = prefs.getString("firstName", "FirstName");
        String lastName=prefs.getString("lastName", "LastName");
        usernameTextView.setText(firstName+" "+lastName);
        //for water counter
        TextView waterCounter = view.findViewById(R.id.water_counter);
        final int[] waterIntake = {0};
        //for water button:
        Button drinkbutton = view.findViewById(R.id.drink_button);
        ImageView overlayView = view.findViewById(R.id.filled_bottle);
        final float[] alpha = {0f};

        drinkbutton.setOnClickListener(v-> {
            if(alpha[0]< 1f) {
                alpha[0] += 0.1f;
                overlayView.setAlpha(alpha[0]);

                waterIntake[0] +=8;
                waterCounter.setText((waterIntake[0] +" oz"));
            }
        });

        editIcon.setOnClickListener(v-> {
            showEdit(usernameTextView);
        });

        View progress_rect = view.findViewById(R.id.progress_rect);
        progress_rect.setOnClickListener(v -> {
            if(getActivity() instanceof TabNavigator) {
                ((TabNavigator) getActivity()).workoutTab();
            }
        });
        View progress_rect2 = view.findViewById(R.id.progress_rect_cal);
        progress_rect2.setOnClickListener(v -> {
            if(getActivity() instanceof TabNavigator) {
                ((TabNavigator) getActivity()).calorieTab();
            }
        });
        return view;


    }
        private void showEdit(TextView usernameTextView) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View dialogView = inflater.inflate(R.layout.edit_profile, null);
            builder.setView(dialogView);

            AlertDialog dialog = builder.create();
            dialog.show();

            EditText firstName = dialogView.findViewById(R.id.firstNameEdit);
            EditText lastName =  dialogView.findViewById(R.id.lastNameEdit);
            EditText heightfeet =  dialogView.findViewById(R.id.heightfeet);
            EditText heightinches =  dialogView.findViewById(R.id.heightinches);
            EditText weight = dialogView.findViewById(R.id.weightinfo);
            Button save = dialogView.findViewById(R.id.savebuttom);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name_1st = firstName.getText().toString();
                    String name_last = lastName.getText().toString();
                    String h_feet = heightfeet.getText().toString();
                    String h_inches = heightinches.getText().toString();
                    String weight_tot = weight.getText().toString();

                    SharedPreferences prefs = requireContext().getSharedPreferences("userProfile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("firstName", name_1st);
                    editor.putString("lastName", name_last);
                    editor.putString("heightFeet", h_feet);
                    editor.putString("heightInches", h_inches);
                    editor.putString("weight", weight_tot);
                    editor.apply();
                    usernameTextView.setText(name_1st+" "+name_last);
                    dialog.dismiss();
                    //to save name, below:


                }
            });

        }
    }
