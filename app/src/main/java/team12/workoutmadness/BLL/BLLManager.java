package team12.workoutmadness.BLL;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import team12.workoutmadness.BE.Day;
import team12.workoutmadness.BE.Workout;
import team12.workoutmadness.DAL.FirebaseDB;
import team12.workoutmadness.GUI.fragments.HomeFragment;

public class BLLManager
{
    private static BLLManager instance = null;
    private FirebaseDB db = null;
    private BLLManager()
    {
        db = new FirebaseDB();
    }

    public static BLLManager getInstance()
    {
        if (instance == null)
            instance = new BLLManager();

        return instance;
    }
    public void setCurrentWorkout(Workout currentWorkout) {
        db.setWorkout(currentWorkout);
    }
    public Workout getCurrentWorkout(){
        return db.getCurrentWorkout();
    }

    public void updateSelectedDay(Day selectedDay) {
        db.updateDay(selectedDay);
    }
    public Day getSelectedDay(){
        return db.getSelectedDay();
    }
    public FirebaseAuth getFirebaseAuth(){
        return db.getFirebaseAuth();
    }
    public FirebaseUser getFirebaseUser(){
        return db.getFirebaseUser();
    }
}