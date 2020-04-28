package team12.workoutmadness;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.MessageFormat;
import java.util.ArrayList;

import team12.workoutmadness.Entity.Day;

class DaysAdapter extends ArrayAdapter<Day> {

    private ArrayList<Day> days;

    DaysAdapter(@NonNull Context context, @NonNull ArrayList<Day> days) {
        super(context, 0, days);
        this.days = days;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_day,null);
        }

        Day day = days.get(position);
        TextView day_name = view.findViewById(R.id.day_name);
        day_name.setText(MessageFormat.format("{0}{1}{2}", day_name.getText().toString(), position, 1));


        ListView daysListView = view.findViewById(R.id.workout_list_view);
        daysListView.removeAllViews();

        return view;
    }
}