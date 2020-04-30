package team12.workoutmadness.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import team12.workoutmadness.R;
import team12.workoutmadness.adapters.ExercisesAdapter;
import team12.workoutmadness.models.Day;
import team12.workoutmadness.models.Exercise;
import team12.workoutmadness.models.Set;

public class DayFragment extends Fragment {
    private static final String TAG = "DAY_FRAGMENT";
    private TextView dayName;
    private Day currentDay;
    private ImageView newExerciseButton;
    private ListView exercisesListView;
    private ArrayList<Exercise> exerciseList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.day, container, false);
        setViews(view);
        newExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set s = new Set(10);
                s.setWeight(50.5);
                ArrayList<Set> arrayList = new  ArrayList<>();
                arrayList.add(s);
                Exercise e = new Exercise("Dead-lift",arrayList);
                exerciseList.add(e);
                setAdapter(view);
            }
        });
        loadDay();
        return  view;
    }

    private void setAdapter(View view) {
        ArrayAdapter arrayAdapter = new ExercisesAdapter(view.getContext(), exerciseList);
        arrayAdapter.notifyDataSetChanged();
        exercisesListView.setAdapter(arrayAdapter);
    }

    private void setViews(View view) {
        dayName = view.findViewById(R.id.day_name);
        newExerciseButton = view.findViewById(R.id.new_exercise_icon);
        exercisesListView = view.findViewById(R.id.exercises_list_view);
    }

    public void setDay(Day currentDay) {
    this.currentDay = currentDay;
    }
    private void loadDay(){
        if(currentDay !=null){
            dayName.setText(currentDay.getName());
        }
    }
}
