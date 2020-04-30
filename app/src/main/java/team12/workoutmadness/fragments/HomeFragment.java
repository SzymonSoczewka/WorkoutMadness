package team12.workoutmadness.fragments;

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

import team12.workoutmadness.adapters.DaysAdapter;
import team12.workoutmadness.views.MainActivity;
import team12.workoutmadness.R;
import team12.workoutmadness.models.Day;
import team12.workoutmadness.models.Workout;

public class HomeFragment extends Fragment {
    private static final String TAG = "HOME_FRAGMENT";
    private Context context;
    private ListView daysListView;
    private TextView title, welcomeLabel;
    private Workout currentWorkout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        this.context = getContext();
        setViews(view);
        if(currentWorkout !=null) {
            loadWorkout(currentWorkout);
        }
        return view;
    }

    private void setViews(View view) {
        daysListView = view.findViewById(R.id.workout_list_view);
        title = view.findViewById(R.id.workout_title);
        welcomeLabel = view.findViewById(R.id.welcome_label);
    }

    public void setWorkout(Workout workout) {
        this.currentWorkout = workout;
        loadWorkout(currentWorkout);
    }

    private void loadWorkout(Workout workout){
            ArrayAdapter arrayAdapter = new DaysAdapter(context, workout.getDays());
            arrayAdapter.notifyDataSetChanged();
            daysListView.setAdapter(arrayAdapter);
            title.setText(workout.getName());
            setListViewListener();
    }

    private void setListViewListener(){
        daysListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Day day = currentWorkout.getDays().get(position);
                ((MainActivity) Objects.requireNonNull(getActivity())).setDay(day);
                ((MainActivity)getActivity()).setViewPager(MainActivity.DAY_FRAGMENT_INDEX);
            }
        });
    }
}
