package team12.workoutmadness.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import team12.workoutmadness.R;
import team12.workoutmadness.adapters.ExercisesAdapter;
import team12.workoutmadness.models.Day;
import team12.workoutmadness.models.Exercise;

public class DayActivity extends AppCompatActivity {
    private static final int DAY_FRAGMENT = 101;
    private TextView dayName;
    private Day selectedDay;
    private ImageView newExerciseButton;
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
        newExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ExerciseActivity.class);
                startActivityForResult(i,DAY_FRAGMENT);
            }
        });
        setAdapter();
        loadDay();
    }
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
    @Override
    public void onBackPressed() {
        if(selectedDay.getExercises()!=null) {
            Intent intent = new Intent();
            intent.putExtra("selectedDay", selectedDay);
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }
    public void setAdapter() {
        if(selectedDay!= null && selectedDay.getExercises() != null){
            arrayAdapter = new ExercisesAdapter(context, selectedDay.getExercises());
            arrayAdapter.notifyDataSetChanged();
            exercisesListView.setAdapter(arrayAdapter);
            exercisesListView.setSelection(selectedDay.getExercises().size()-1);
        }
    }

    private void setViews() {
        dayName = findViewById(R.id.day_name);
        newExerciseButton = findViewById(R.id.new_exercise_icon);
        exercisesListView = findViewById(R.id.exercises_list_view);
    }


    private void loadDay(){
        if(selectedDay!= null && !selectedDay.getName().isEmpty()){
            dayName.setText(selectedDay.getName());
        }
    }
}
