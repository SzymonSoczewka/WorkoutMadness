package team12.workoutmadness.BLL;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import team12.workoutmadness.BE.Day;
import team12.workoutmadness.BE.Workout;
import team12.workoutmadness.DAL.FirebaseDB;

public class BLLManager
{
    private static BLLManager instance = null;
    private FirebaseDB firebase;
    private BLLManager()
    {
        firebase = new FirebaseDB();
    }

    public static BLLManager getInstance()
    {
        if (instance == null)
            instance = new BLLManager();

        return instance;
    }
    public void setCurrentWorkout(Workout currentWorkout) {
        firebase.setWorkout(currentWorkout);
    }
    public Workout getCurrentWorkout(){
        return firebase.getCurrentWorkout();
    }

    public void updateSelectedDay(Day selectedDay) {
        firebase.updateDay(selectedDay);
    }
    public Day getSelectedDay(){
        return firebase.getSelectedDay();
    }
    public FirebaseAuth getFirebaseAuth(){
        return firebase.getFirebaseAuth();
    }
    public FirebaseUser getFirebaseUser(){
        return firebase.getFirebaseUser();
    }
}