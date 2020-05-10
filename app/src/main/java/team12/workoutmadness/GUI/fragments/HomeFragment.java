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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import team12.workoutmadness.BLL.BLLManager;
import team12.workoutmadness.GUI.adapters.DaysAdapter;
import team12.workoutmadness.GUI.activities.DayActivity;
import team12.workoutmadness.GUI.activities.MainActivity;
import team12.workoutmadness.R;
import team12.workoutmadness.BE.Day;
import team12.workoutmadness.BE.Workout;

public class HomeFragment extends Fragment {
    private static final int HOME_FRAGMENT = 102;
    private BLLManager manager = BLLManager.getInstance();
    private Context context;
    private ListView daysListView;
    private TextView title, welcomeLabel;
    private Workout workout;
    private ImageButton btnChangeName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        this.context = getContext();
        setViews(view);
        welcomeLabel.setText("Welcome "+manager.getFirebaseUser().getDisplayName());
        workout = manager.getCurrentWorkout();
        if(workout !=null) {
            loadWorkout(workout);
        }
        return view;
    }
    //Temporary method to load the workout when the fragment is resumed
    @Override
    public void onResume() {
        System.out.println("was resumed");
        workout = manager.getCurrentWorkout();
        System.out.println("Is it null? "+workout);
        if(workout!=null)
        loadWorkout(workout);
        super.onResume();
    }

    //Getting access to .xml elements
    private void setViews(View view) {
        daysListView = view.findViewById(R.id.workout_list_view);
        title = view.findViewById(R.id.workout_title);
        welcomeLabel = view.findViewById(R.id.welcome_label);
        btnChangeName = view.findViewById(R.id.btn_change_name);
        btnChangeName.setVisibility(View.INVISIBLE);
    }

    //This method is responsible for loading content for this fragment as soon as it contains workout
    @SuppressLint("SetTextI18n")
    private void loadWorkout(Workout workout){
            ArrayAdapter arrayAdapter = new DaysAdapter(context, workout.getDays());
            arrayAdapter.notifyDataSetChanged();
            daysListView.setAdapter(arrayAdapter);
            title.setText(workout.getName());
            btnChangeName.setVisibility(View.VISIBLE);
            setListViewListener();
            setChangeNameButton();
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
                //((MainActivity) Objects.requireNonNull(getActivity())).setSelectedDay(day);
                //((MainActivity)getActivity()).setViewPager(MainActivity.DAY_FRAGMENT_INDEX);
                Intent i = new Intent(context, DayActivity.class);
                i.putExtra("selectedDay",day);
                startActivityForResult(i,HOME_FRAGMENT);
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
                //((MainActivity) Objects.requireNonNull(getActivity())).updateSelectedDay(day);
                manager.updateSelectedDay(day);
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
                //((MainActivity) Objects.requireNonNull(getActivity())).setCurrentWorkout(workout);
                manager.setCurrentWorkout(workout);
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
                workout.getDays().remove(position);
                //((MainActivity) Objects.requireNonNull(getActivity())).setCurrentWorkout(workout);
                manager.setCurrentWorkout(workout);
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
