package team12.workoutmadness.GUI.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import team12.workoutmadness.BE.Day;
import team12.workoutmadness.BE.Workout;
import team12.workoutmadness.R;
import team12.workoutmadness.BE.Exercise;
import team12.workoutmadness.BE.Set;

public class ExerciseActivity extends AppCompatActivity {
    private ArrayList<LinearLayout> setRowsList = new ArrayList<>();
    private Button btnNewSet,btnSaveExercise;
    private LinearLayout setsContainer;
    private Exercise exerciseToUpdate;
    private Spinner exerciseSpinner;
    private EditText nameInput;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);
        context = getWindow().getContext();
        setViews();
        setButtons();
        setSpinner();
        exerciseToUpdate = (Exercise) getIntent().getSerializableExtra("exerciseToUpdate");
        if(exerciseToUpdate!=null){
            prepareForUpdate();
        }
    }
    //This method will load all the data of the "exerciseToUpdate"
    private void prepareForUpdate() {
        nameInput.setText(exerciseToUpdate.getName());
        for (int i=0; i<exerciseToUpdate.getSets().size(); i++){
            createNewSet(true, i);
        }
    }

    //Getting access to .xml elements
    private void setViews() {
        btnNewSet = findViewById(R.id.btn_new_set);
        nameInput = findViewById(R.id.exercise_name_input);
        btnSaveExercise = findViewById(R.id.save_exercise);
        setsContainer = findViewById(R.id.sets_container);
        exerciseSpinner = findViewById(R.id.exercise_spinner);
    }
    //This method overrides behaviour when back button is pressed
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED,intent);
        finish();
    }
    //This method sets functionality for buttons
    private void setButtons() {
        //When this button is clicked, new row appears (max 10 rows)
        btnNewSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setRowsList.size()<10) {
                   createNewSet(false,0);
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
                    Toast.makeText(context,"Name the exercise and insert reps for all sets",Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void setSpinner(){
        final List<String> exercises = new ArrayList<>();
        exercises.add("Push-ups");
        exercises.add("Pull-ups");
        exercises.add("Dead-lift");
        exercises.add("Squats");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.workout_spinner, exercises);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        exerciseSpinner.setAdapter(dataAdapter);

        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!nameInput.getText().toString().isEmpty())
                nameInput.setText(exercises.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    //This method is creating all elements for a row in which user provides his input
    private void createNewSet(boolean updateMode, int setIndex) {
        final Context context = getWindow().getContext();

        LinearLayout row = new LinearLayout(context);
        EditText repsInput = new EditText(context);
        EditText weightInput = new EditText(context);

        if(updateMode) {
            Set set = exerciseToUpdate.getSets().get(setIndex);
            repsInput.setText(set.getReps().toString());
            if(set.getWeight()!=null)
                weightInput.setText(set.getWeight().toString());
        }

        ImageButton removeRowButton = new ImageButton(context);
        setInputTextAttributes(repsInput, weightInput, removeRowButton, row);
        row.addView(repsInput);
        row.addView(weightInput);
        row.addView(removeRowButton);
        setsContainer.addView(row);
        setRowsList.add(row);
    }

    //This method retrieves user input and based on it creates Exercise object and adds it to the Workout
    private void createExercise() {
        ArrayList<Set> sets = new ArrayList<>();
        for(int i=0; i<setRowsList.size(); i++) {
            EditText repsInput = (EditText) setRowsList.get(i).getChildAt(0);
            int reps = Integer.parseInt(repsInput.getText().toString());
            EditText weightInput = (EditText) setRowsList.get(i).getChildAt(1);
            if(weightInput.getText().length() == 0 ){
                sets.add(new Set(reps));
            } else{
                Set s = new Set(reps);
                double weight = Double.parseDouble(weightInput.getText().toString());
                s.setWeight(weight);
                sets.add(s);
            }
        }
        setRowsList.clear();
        String exerciseName = nameInput.getText().toString();
        Exercise exercise = new Exercise(exerciseName,sets);
        finishIntent(exercise);
    }
    //This method will finish current intent
    private void finishIntent(Exercise exercise){
        Intent intent = new Intent();
        if(exerciseToUpdate == null)
            intent.putExtra("newExercise",exercise);
        else
            intent.putExtra("exerciseUpdated",exercise);
        setResult(Activity.RESULT_OK,intent);
        finish();
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
