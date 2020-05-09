package team12.workoutmadness.GUI.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import team12.workoutmadness.R;
import team12.workoutmadness.GUI.adapters.ExercisesAdapter;
import team12.workoutmadness.BE.Day;
import team12.workoutmadness.BE.Exercise;

public class DayActivity extends AppCompatActivity {
    private static final int DAY_FRAGMENT = 101;
    private TextView dayName;
    private Day selectedDay;
    private Button btnNewExercise,btnReturn;
    private ListView exercisesListView;
    private ArrayAdapter arrayAdapter;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day);
        context = getWindow().getContext();
        selectedDay = (Day) getIntent().getSerializableExtra("selectedDay");
        setViews();
        setButtons();
        setAdapter();
        loadDay();
    }



    //Getting access to .xml elements
    private void setViews() {
        dayName = findViewById(R.id.day_name);
        btnNewExercise = findViewById(R.id.btn_new_exercise);
        btnReturn = findViewById(R.id.btn_return_to_Home_Fragment);
        exercisesListView = findViewById(R.id.exercises_list_view);
    }
    //This method sets buttons behaviour
    private void setButtons() {
        btnNewExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ExerciseActivity.class);
                startActivityForResult(i,DAY_FRAGMENT);
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDayActivity();
            }
        });
    }
    //This method is responsible for handling finished Intent in ExerciseActivity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DAY_FRAGMENT) {
            if (resultCode == Activity.RESULT_OK) {
                Exercise newExercise =  (Exercise) data.getExtras().getSerializable("newExercise");
                selectedDay.addExercise(newExercise);
                setAdapter();
            }
        }
    }
    //This method overrides behaviour when back button is pressed
    @Override
    public void onBackPressed() {
        finishDayActivity();
    }
    //This method is saving changed if any occurred and finishes this activity
    private void finishDayActivity() {
        if(selectedDay.getExercises()!=null) {
            Intent intent = new Intent();
            intent.putExtra("selectedDay", selectedDay);
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }

    //This method is responsible for loading exercisesListView with data
    public void setAdapter() {
        if(selectedDay!= null && selectedDay.getExercises() != null){
            arrayAdapter = new ExercisesAdapter(context, selectedDay.getExercises());
            arrayAdapter.notifyDataSetChanged();
            exercisesListView.setAdapter(arrayAdapter);
            exercisesListView.setSelection(selectedDay.getExercises().size()-1);
            setListViewListener();
        }
    }

    //This method simply sets the name that user is currently in
    private void loadDay(){
        if(selectedDay!= null && !selectedDay.getName().isEmpty()){
            dayName.setText(selectedDay.getName());
        }
    }

    private void setListViewListener(){
        //If user is pressing  ListView item longer, delete exercise dialog will appear
        exercisesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteExerciseDialog(position);
                return true;
            }
        });
        //If user simply clicks on the item, he will see Exercise details
        exercisesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    //This method presents dialog for the user where he can delete exercise from the day
    private void showDeleteExerciseDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Do you really want to remove this exercise?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedDay.getExercises().remove(position);
                setAdapter();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
