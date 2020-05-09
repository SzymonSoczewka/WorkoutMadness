package team12.workoutmadness.GUI.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import team12.workoutmadness.R;
import team12.workoutmadness.BE.Day;

public class DaysAdapter extends ArrayAdapter<Day> {

    private ArrayList<Day> days;

    public DaysAdapter(@NonNull Context context, @NonNull ArrayList<Day> days) {
        super(context, 0, days);
        this.days = days;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.day_lv_element,null);
        }

        Day day = days.get(position);
        TextView dayName = view.findViewById(R.id.day_name);
        dayName.setText(day.getName());
        TextView exercisesCount = view.findViewById(R.id.exercises_count);
        exercisesCount.setTextSize(20);
        exercisesCount.setText(day.getExercises().size() + " exercise(s)");
        view.setPadding(0,0,0,30);
        return view;
    }
}