package team12.workoutmadness;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import team12.workoutmadness.models.Workout;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        setViews(view);
        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            Workout workout = (Workout) bundle.getSerializable("workout");
            saveWorkout(workout, view.getContext());
        }
        return view;
    }

    private void setViews(View view) {
        listView = view.findViewById(R.id.workout_list_view);
    }

    private void saveWorkout(Workout workout, Context context){
        ArrayAdapter arrayAdapter = new DaysAdapter(context, workout.getDays());
        arrayAdapter.notifyDataSetChanged();
        listView.setAdapter(arrayAdapter);
    }
}
