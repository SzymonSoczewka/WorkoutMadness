package team12.workoutmadness.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import team12.workoutmadness.R;
import team12.workoutmadness.models.Day;
import team12.workoutmadness.models.Exercise;
import team12.workoutmadness.models.Set;
import team12.workoutmadness.views.MainActivity;

public class ExerciseFragment extends Fragment {
    private Button btnNewSet;
    private TextInputEditText nameInput;
    private EditText repsInput,weightInput;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise, container, false);
        setViews(view);
        btnNewSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exerciseName = Objects.requireNonNull(nameInput.getText()).toString();
                String reps = repsInput.getText().toString();
                if(!exerciseName.isEmpty() && !reps.isEmpty()){
                    Set s = new Set(Integer.parseInt(reps));
                    ArrayList<Set> ar = new ArrayList<Set>();
                    ar.add(s);
                    Exercise e = new Exercise(exerciseName,ar);
                    Day d = ((MainActivity) Objects.requireNonNull(getActivity())).getSelectedDay();
                    d.addExercise(e);
                }
            }
        });
        return view;
    }

    private void setViews(View view) {
        btnNewSet = view.findViewById(R.id.btn_new_set);
        nameInput = view.findViewById(R.id.exercise_name_input);
        repsInput = view.findViewById(R.id.reps_input);
    }
}
