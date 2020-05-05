package team12.workoutmadness.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import team12.workoutmadness.R;
import team12.workoutmadness.models.Day;
import team12.workoutmadness.models.Exercise;
import team12.workoutmadness.models.Set;
import team12.workoutmadness.views.MainActivity;

public class ExerciseFragment extends Fragment {
    private Button btnNewSet,btnSaveExercise;
    private EditText nameInput;
    private LinearLayout setsContainer;
    private ArrayList<LinearLayout> setRowsList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.exercise, container, false);
        setViews(view);
        setButtons(view);
        return view;
    }

    //Getting access to .xml elements
    private void setViews(View view) {
        btnNewSet = view.findViewById(R.id.btn_new_set);
        nameInput = view.findViewById(R.id.exercise_name_input);
        btnSaveExercise = view.findViewById(R.id.save_exercise);
        setsContainer = view.findViewById(R.id.sets_container);
    }
    //This method sets functionality for buttons
    private void setButtons(final View view) {
        //When this button is clicked, new row appears
        btnNewSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setRowsList.size()<10) {
                    LinearLayout row = new LinearLayout(view.getContext());
                    EditText repsInput = new EditText(view.getContext());
                    EditText weightInput = new EditText(view.getContext());
                    ImageButton removeRowButton = new ImageButton(view.getContext());
                    setInputTextAttributes(repsInput, weightInput, removeRowButton, row);
                    row.addView(repsInput);
                    row.addView(weightInput);
                    row.addView(removeRowButton);
                    setsContainer.addView(row);
                    setRowsList.add(row);
                }
            }
        });
        //When this button is clicked, user's input is validated and either Exercise object created
        //or Toast presented on the screen
        btnSaveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs())
                    createExercise();
                else
                    Toast.makeText(view.getContext(),"Name the exercise and insert reps for all sets",Toast.LENGTH_SHORT).show();

            }
        });
    }
    //This method retrieves user input and based on it creates Exercise object and adds it to the Workout
    private void createExercise() {
        ArrayList<Set> sets = new ArrayList<>();
        for(int i=0; i<setRowsList.size(); i++) {
            EditText repsInput = (EditText) setRowsList.get(i).getChildAt(0);
            int reps = Integer.parseInt(repsInput.getText().toString());
            EditText weightInput = (EditText) setRowsList.get(i).getChildAt(1);
            double weight = Double.parseDouble(repsInput.getText().toString());
            if(weightInput.getText().length() == 0 ){
                sets.add(new Set(reps));
            } else{
                Set s = new Set(reps);
                s.setWeight(weight);
                sets.add(s);
            }
        }
        setRowsList.clear();
        String exerciseName = nameInput.getText().toString();
        Exercise exercise = new Exercise(exerciseName,sets);
        Day selectedDay = ((MainActivity)getActivity()).getSelectedDay();
        selectedDay.addExercise(exercise);
        ((MainActivity)getActivity()).updateSelectedDay(selectedDay);
        ((MainActivity)getActivity()).setViewPager(MainActivity.DAY_FRAGMENT_INDEX);
    }
    //This method is responsible for styling of the programmatically added views
    private void setInputTextAttributes(EditText reps, EditText weight, final ImageButton removeSetButton, final LinearLayout row) {
        reps.setInputType(InputType.TYPE_CLASS_NUMBER);
        reps.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});
        reps.setWidth(200);
        reps.layout(0,0,30,0);
        reps.setHint("reps");
        weight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        weight.setFilters(new InputFilter[] {new InputFilter.LengthFilter(5)});
        weight.setWidth(200);
        weight.setHint("weight");
        removeSetButton.setBackgroundResource(R.drawable.delete_32px);
        removeSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup)v.getParent().getParent()).removeView((ViewGroup)v.getParent());
                setRowsList.remove(row);
            }
        });
    }
    //This method is checking user inputs and based on them, gives a green or red light for creating Exercise object
    private boolean validateInputs(){
        if(setRowsList.size()==0)
            return false;
        if(nameInput!= null && Objects.requireNonNull(nameInput.getText()).toString().length() == 0)
            return false;
        for(int i=0; i<setRowsList.size(); i++) {
            EditText x = (EditText) setRowsList.get(i).getChildAt(0);
            if (x.getText() == null || x.getText().length() == 0)
                return false;
        }
        return true;
    }

}
