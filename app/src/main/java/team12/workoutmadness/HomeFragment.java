package team12.workoutmadness;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import team12.workoutmadness.models.Day;
import team12.workoutmadness.models.Workout;

public class HomeFragment extends Fragment {
    private static final String TAG = "HOME_FRAGMENT";
    private Context context;
    private ListView list_view;
    private TextView title, welcome_label;
    private Workout current_workout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        this.context = getContext();
        setViews(view);
        if(current_workout !=null) {
            loadWorkout(current_workout);
        }
        return view;
    }

    private void setViews(View view) {
        list_view = view.findViewById(R.id.workout_list_view);
        title = view.findViewById(R.id.workout_title);
        welcome_label = view.findViewById(R.id.welcome_label);
    }

    public void setWorkout(Workout workout) {
        this.current_workout = workout;
        loadWorkout(current_workout);
    }

    private void loadWorkout(Workout workout){
            ArrayAdapter arrayAdapter = new DaysAdapter(context, workout.getDays());
            arrayAdapter.notifyDataSetChanged();
            list_view.setAdapter(arrayAdapter);
            title.setText(workout.getName());
            setListViewListener();
    }

    private void setListViewListener(){
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Day day = current_workout.getDays().get(position);
                ((MainActivity) Objects.requireNonNull(getActivity())).setDay(day);
                ((MainActivity)getActivity()).setView_pager(MainActivity.DAY_FRAGMENT_INDEX);
            }
        });
    }
}
