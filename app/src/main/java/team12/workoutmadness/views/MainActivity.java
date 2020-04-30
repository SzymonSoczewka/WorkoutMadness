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
import team12.workoutmadness.fragments.DayFragment;
import team12.workoutmadness.fragments.HomeFragment;
import team12.workoutmadness.fragments.NewWorkoutFragment;
import team12.workoutmadness.fragments.ProfileFragment;
import team12.workoutmadness.models.Day;
import team12.workoutmadness.models.Workout;


public class MainActivity extends AppCompatActivity {
    public static final int HOME_FRAGMENT_INDEX = 0;
    public static final int NEW_WORKOUT_FRAGMENT_INDEX = 1;
    public static final int PROFILE_FRAGMENT_INDEX = 2;
    public static final int DAY_FRAGMENT_INDEX = 3;
    private Button btnHome, btnNew, btnProfile;
    private ViewPager view_pager;
    SectionsPagerAdapter adapter;
    Workout current_workout;
    Day current_day;


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
        setupViewPager(view_pager);



        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView_pager(HOME_FRAGMENT_INDEX);
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView_pager(NEW_WORKOUT_FRAGMENT_INDEX);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView_pager(PROFILE_FRAGMENT_INDEX);
            }
        });

    }

    private void setViews() {
        view_pager = findViewById(R.id.container);
        btnHome = findViewById(R.id.btnHome);
        btnNew = findViewById(R.id.btnNew);
        btnProfile = findViewById(R.id.btnProfile);
    }

    private void setupViewPager(ViewPager viewPager){
        adapter = new SectionsPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new HomeFragment(), "HOME");
        adapter.addFragment(new NewWorkoutFragment(), "NEW_WORKOUT");
        adapter.addFragment(new ProfileFragment(), "PROFILE");
        adapter.addFragment(new DayFragment(),"DAY");
        viewPager.setAdapter(adapter);
    }

    public void setView_pager(int fragment_index){
        view_pager.setCurrentItem(fragment_index);
    }

    public void setWorkout(Workout workout) {
        current_workout = workout;
        HomeFragment hf = (HomeFragment) adapter.getItem(HOME_FRAGMENT_INDEX);
        hf.setWorkout(current_workout);
    }
    public void setDay(Day day) {
        current_day = day;
        DayFragment df = (DayFragment) adapter.getItem(DAY_FRAGMENT_INDEX);
        df.setDay(current_day);
    }
}
