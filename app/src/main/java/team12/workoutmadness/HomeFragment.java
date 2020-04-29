package team12.workoutmadness;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import team12.workoutmadness.models.Workout;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private Context context;
    private ListView listView;
    private TextView title;
    private Workout currentWorkout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        this.context = getContext();
        setViews(view);
        if(currentWorkout!=null) {
            loadWorkout(currentWorkout);
        }
        return view;
    }

    private void setViews(View view) {
        listView = view.findViewById(R.id.workout_list_view);
        title = view.findViewById(R.id.workout_title);
    }

    private void loadWorkout(Workout workout){
            ArrayAdapter arrayAdapter = new DaysAdapter(context, workout.getDays());
            arrayAdapter.notifyDataSetChanged();
            listView.setAdapter(arrayAdapter);
    }

    public void setWorkout(Workout workout) {
        this.currentWorkout = workout;
        loadWorkout(currentWorkout);
    }
}
