package team12.workoutmadness.adapters;

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
import team12.workoutmadness.models.Day;

public class DaysAdapter extends ArrayAdapter<Day> {

    private ArrayList<Day> days;

    public DaysAdapter(@NonNull Context context, @NonNull ArrayList<Day> days) {
        super(context, 0, days);
        this.days = days;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_element,null);
        }

        Day day = days.get(position);
        TextView day_name = view.findViewById(R.id.day_name);
        day_name.setText(day.getName());
        view.getPaddingBottom();

        return view;
    }
}