package team12.workoutmadness.views;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import team12.workoutmadness.R;
import team12.workoutmadness.adapters.SectionsPagerAdapter;
import team12.workoutmadness.fragments.DayFragment;
import team12.workoutmadness.fragments.ExerciseFragment;
import team12.workoutmadness.fragments.HomeFragment;
import team12.workoutmadness.fragments.NewWorkoutFragment;
import team12.workoutmadness.fragments.ProfileFragment;
import team12.workoutmadness.models.Day;
import team12.workoutmadness.models.Exercise;
import team12.workoutmadness.models.Workout;


public class MainActivity extends AppCompatActivity {
    public static final int HOME_FRAGMENT_INDEX = 0;
    public static final int NEW_WORKOUT_FRAGMENT_INDEX = 1;
    public static final int PROFILE_FRAGMENT_INDEX = 2;
    public static final int DAY_FRAGMENT_INDEX = 3;
    public static final int EXERCISE_FRAGMENT_INDEX = 4;
    private Button btnHome, btnNew, btnProfile;
    private ViewPager viewPager;
    SectionsPagerAdapter adapter;
    Workout currentWorkout;
    Day selectedDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        if(user == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new NewWorkoutFragment()).commit();
        setViews();
        setupViewPager(viewPager);



        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewPager(HOME_FRAGMENT_INDEX);
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewPager(NEW_WORKOUT_FRAGMENT_INDEX);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewPager(PROFILE_FRAGMENT_INDEX);
            }
        });

    }

    private void setViews() {
        viewPager = findViewById(R.id.container);
        btnHome = findViewById(R.id.btnHome);
        btnNew = findViewById(R.id.btnNew);
        btnProfile = findViewById(R.id.btnProfile);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupViewPager(final ViewPager viewPager){
        adapter = new SectionsPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new HomeFragment(), "HOME");
        adapter.addFragment(new NewWorkoutFragment(), "NEW_WORKOUT");
        adapter.addFragment(new ProfileFragment(), "PROFILE");
        adapter.addFragment(new DayFragment(),"DAY");
        adapter.addFragment(new ExerciseFragment(),"EXERCISE");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragment_index){
        viewPager.setCurrentItem(fragment_index);
    }

    public void setCurrentWorkout(Workout workout) {
        currentWorkout = workout;
        HomeFragment hf = (HomeFragment) adapter.getItem(HOME_FRAGMENT_INDEX);
        hf.setWorkout(currentWorkout);
    }

    public void setSelectedDay(Day day) {
        selectedDay = day;
        DayFragment df = (DayFragment) adapter.getItem(DAY_FRAGMENT_INDEX);
        df.setSelectedDay(selectedDay);
    }
    public void updateSelectedDay(Day day) {
        selectedDay = day;
        currentWorkout.updateDay(selectedDay);
        DayFragment df = (DayFragment) adapter.getItem(DAY_FRAGMENT_INDEX);
        df.setAdapter();
    }
    public Day getSelectedDay(){
        return selectedDay;
    }

}
