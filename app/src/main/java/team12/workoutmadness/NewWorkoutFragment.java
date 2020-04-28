package team12.workoutmadness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import team12.workoutmadness.models.Day;
import team12.workoutmadness.models.Workout;

public class NewWorkoutFragment extends Fragment {
    private static final String TAG = "NewWorkoutFragment";
    private Button btnSave;
    private EditText workout_name;
    private CheckBox mon,tue,wed,thur,fri,sat,sun;
    public NewWorkoutFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.new_workout, container, false);
        setViews(view);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workout_name.getText()!=null) {
                    Bundle bundle = new Bundle();
                    String workoutName = workout_name.getText().toString();
                    Workout workout = new Workout(workoutName, getSelectedDays());
                    bundle.putSerializable("workout", workout);
                    HomeFragment homeFragment = new HomeFragment();
                    homeFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    ((MainActivity)getActivity()).setViewPager(0);
                }
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

    }
    private void setViews(View view){
        //Button
        btnSave = view.findViewById(R.id.btnSave);
        //EditText
        workout_name = view.findViewById(R.id.workout_name);
        //CheckBox
        mon= view.findViewById(R.id.monday_box);
        tue= view.findViewById(R.id.tuesday_box);
        wed= view.findViewById(R.id.wednesday_box);
        thur= view.findViewById(R.id.thursday_box);
        fri= view.findViewById(R.id.friday_box);
        sat= view.findViewById(R.id.saturday_box);
        sun= view.findViewById(R.id.sunday_box);
    }



    private ArrayList<Day> getSelectedDays(){
        CheckBox[] checkBoxes = new CheckBox[]{mon,tue,wed,thur,fri,sat,sun};
        ArrayList<Day> days = new ArrayList<>();
        for (CheckBox cb: checkBoxes) {
            if(cb.isChecked())
                days.add(new Day(cb.getText().toString()));
        }
        return days;
    }


}
