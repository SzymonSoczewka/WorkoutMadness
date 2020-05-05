package team12.workoutmadness.fragments;

import android.content.Context;
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

import team12.workoutmadness.R;
import team12.workoutmadness.adapters.ExercisesAdapter;
import team12.workoutmadness.models.Day;
import team12.workoutmadness.views.MainActivity;

public class DayFragment extends Fragment {
    private static final String TAG = "DAY_FRAGMENT";
    private TextView dayName;
    private Day selectedDay;
    private ImageView newExerciseButton;
    private ListView exercisesListView;
    private ArrayAdapter arrayAdapter;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.day, container, false);
        context = view.getContext();
        setViews(view);
        newExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setViewPager(MainActivity.EXERCISE_FRAGMENT_INDEX);
            }
        });
        setAdapter();
        loadDay();
        return  view;
    }
    @Override
    public void onDestroyView() {
        System.out.println("Destroyed - Day Fragment");
        super.onDestroyView();
    }
    public void setAdapter() {
        if(selectedDay!= null && selectedDay.getExercises() != null){
            arrayAdapter = new ExercisesAdapter(context, selectedDay.getExercises());
            arrayAdapter.notifyDataSetChanged();
            exercisesListView.setAdapter(arrayAdapter);
            exercisesListView.setSelection(selectedDay.getExercises().size()-1);
        }
    }

    private void setViews(View view) {
        dayName = view.findViewById(R.id.day_name);
        newExerciseButton = view.findViewById(R.id.new_exercise_icon);
        exercisesListView = view.findViewById(R.id.exercises_list_view);
    }

    public void setSelectedDay(Day currentDay) {
        this.selectedDay = currentDay;
    }
    private void loadDay(){
        if(selectedDay!= null && !selectedDay.getName().isEmpty()){
            dayName.setText(selectedDay.getName());
        }
    }
}
