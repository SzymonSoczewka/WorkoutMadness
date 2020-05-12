package team12.workoutmadness.GUI.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import team12.workoutmadness.BLL.BLLManager;
import team12.workoutmadness.GUI.activities.MainActivity;
import team12.workoutmadness.GUI.adapters.DaysAdapter;
import team12.workoutmadness.GUI.activities.DayActivity;
import team12.workoutmadness.R;
import team12.workoutmadness.BE.Day;
import team12.workoutmadness.BE.Workout;

public class HomeFragment extends Fragment {
    private static final int HOME_FRAGMENT = 102;
    private BLLManager manager = BLLManager.getInstance(null);
    private Context context;
    private ListView daysListView;
    private TextView welcomeLabel;
    private Spinner title;
    private ArrayList<Workout> workouts;
    private Workout workout;
    private ImageButton btnChangeName;
    private Button newWorkoutButton;
    private ArrayAdapter arrayAdapter;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        this.context = getContext();
        setViews(view);
        setNewWorkoutButton();
        welcomeLabel.setText("Welcome "+ manager.getFirebaseUsername());
        workouts = manager.getWorkouts();
        if(workouts.size()>0) {
            workout = workouts.get(0);
            setVisibilityWithWorkouts();
            setSpinner();
            setTitleSpinnerListener();
            loadWorkout(workout);
        }
        return view;
    }
    //This method loads the workout when the fragment is resumed
    @Override
    public void onResume() {
        System.out.println("was resumed");
        workouts = manager.getWorkouts();
        if(workouts.size()>0) {
            workout = workouts.get(0);
            setSpinner();
            setVisibilityWithWorkouts();
            loadWorkout(workout);
        }
        super.onResume();
    }

    //Getting access to .xml elements
    private void setViews(View view) {
        daysListView = view.findViewById(R.id.workout_list_view);
        title = view.findViewById(R.id.workout_title);
        welcomeLabel = view.findViewById(R.id.welcome_label);
        btnChangeName = view.findViewById(R.id.btn_change_name);
        newWorkoutButton = view.findViewById(R.id.new_workout_button);
        setVisibilityNoWorkouts();
    }

    //This method is responsible for loading content for this fragment as soon as it contains workout
    private void loadWorkout(Workout workout){
            arrayAdapter = new DaysAdapter(context, workout.getDays());
            arrayAdapter.notifyDataSetChanged();
            daysListView.setAdapter(arrayAdapter);
            setListViewListener();
            setChangeNameButton();
    }
    //Some items might appear or disappear if there is no workout in DB
    private void setVisibilityWithWorkouts(){
        btnChangeName.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        newWorkoutButton.setVisibility(View.INVISIBLE);
    }
    //Some items might appear or disappear if there is no workout in DB
    private void setVisibilityNoWorkouts(){
        newWorkoutButton.setVisibility(View.VISIBLE);
        btnChangeName.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
    }
    //This method is loading all workouts names to the spinner
    private void setSpinner() {
        List<String> workoutsNames = new ArrayList<>();
        for (Workout w : workouts) {
            workoutsNames.add(w.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, R.layout.workout_spinner, workoutsNames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        title.setAdapter(dataAdapter);

    }
    //This method configures button behaviour
    private void setChangeNameButton() {
        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeNameDialog();
            }
        });
    }
    private void setNewWorkoutButton(){
        newWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).setViewPager(MainActivity.NEW_WORKOUT_FRAGMENT_INDEX);

            }
        });
    }
    //This method is defines different listView behaviours based on different types of user behaviour - longPress/click
    private void setListViewListener(){
        //If user is pressing  ListView item longer, delete day dialog will appear
        daysListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDayDialog(position);
                return true;
            }
        });
        //If user simply clicks on the item, he will see Day details
        daysListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Day day = workout.getDays().get(position);
                Intent i = new Intent(context, DayActivity.class);
                i.putExtra("selectedDay",day);
                startActivityForResult(i,HOME_FRAGMENT);
            }
        });
    }
    //This method sets listener for the spinner thereby user can switch between his workouts
    private void setTitleSpinnerListener(){
        title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            workout = workouts.get(position);
            loadWorkout(workout);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    //This method is responsible for handling finished Intent in DayActivity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HOME_FRAGMENT) {
            if (resultCode == Activity.RESULT_OK) {
                Day day =  (Day) data.getExtras().getSerializable("selectedDay");
                workout.updateDay(day);
                manager.updateWorkout(workout);
            }
        }
    }
    //This method presents dialog for the user where he can change workout name
    private void showChangeNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Set new workout name.");
        // Set up the input
        final EditText input = new EditText(getContext());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = input.getText().toString();
                workout.setName(newName);
                manager.updateWorkout(workout);
                workouts = manager.getWorkouts();
                setSpinner();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    //This method presents dialog for the user where he can delete day from the workout
    private void showDeleteDayDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Do you really want to remove this day?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //If user deletes the last day that workout contains, workout will be deleted as well
                if(workout.getDays().size()==1){
                    manager.deleteWorkout(workout.getId());
                    workouts = manager.getWorkouts();
                    if(workouts.size()>0) {
                        workout = workouts.get(0);
                        loadWorkout(workout);
                        setSpinner();
                    }else {
                        setVisibilityNoWorkouts();
                        daysListView.setAdapter(null);
                    }
                }
                else {
                    workout.getDays().remove(position);
                    manager.updateWorkout(workout);
                    loadWorkout(workout);
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
