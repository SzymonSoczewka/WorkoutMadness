package team12.workoutmadness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import team12.workoutmadness.models.Day;

public class DayFragment extends Fragment {
    private TextView day_name;
    private Day current_day;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day, container, false);
        setViews(view);
        loadDay();
        return  view;
    }
    private void setViews(View view) {
        day_name = view.findViewById(R.id.day_name);
    }

    void setDay(Day currentDay) {
    this.current_day = currentDay;
    }
    private void loadDay(){
        if(current_day!=null){
            day_name.setText(current_day.getName());
        }
    }
}
