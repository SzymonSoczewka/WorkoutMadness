package team12.workoutmadness.views;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import team12.workoutmadness.R;
import team12.workoutmadness.adapters.SectionsPagerAdapter;
import team12.workoutmadness.fragments.BodyPartsFragment;
import team12.workoutmadness.fragments.HomeFragment;
import team12.workoutmadness.fragments.NewWorkoutFragment;
import team12.workoutmadness.fragments.ProfileFragment;
import team12.workoutmadness.models.Day;
import team12.workoutmadness.models.Workout;


public class MainActivity extends AppCompatActivity {
    public static final int HOME_FRAGMENT_INDEX = 0;
    public static final int NEW_WORKOUT_FRAGMENT_INDEX = 1;
    public static final int BODY_PARTS_FRAGMENT_INDEX = 2;
    public static final int PROFILE_FRAGMENT_INDEX = 3;
    private Button btnHome, btnNew, btnProfile, btnBodyParts;
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



        setButtons();
    }



    private void setViews() {
        viewPager = findViewById(R.id.container);
        btnHome = findViewById(R.id.btn_home);
        btnNew = findViewById(R.id.btn_new_workout);
        btnProfile = findViewById(R.id.btn_profile);
        btnBodyParts = findViewById(R.id.btn_body_parts);
    }
    private void setButtons() {
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
        btnBodyParts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewPager(BODY_PARTS_FRAGMENT_INDEX);
            }
        });
    }
    private void setupViewPager(final ViewPager viewPager){
        adapter = new SectionsPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new HomeFragment(), "HOME");
        adapter.addFragment(new NewWorkoutFragment(), "NEW_WORKOUT");
        adapter.addFragment(new BodyPartsFragment(),"BODY_PARTS");
        adapter.addFragment(new ProfileFragment(), "PROFILE");
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

    public void updateSelectedDay(Day day) {
        selectedDay = day;
        currentWorkout.updateDay(selectedDay);
        HomeFragment df = (HomeFragment) adapter.getItem(HOME_FRAGMENT_INDEX);
        df.setWorkout(currentWorkout);
    }
    public Day getSelectedDay(){
        return selectedDay;
    }

}
