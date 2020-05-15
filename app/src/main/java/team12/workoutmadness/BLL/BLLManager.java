package team12.workoutmadness.BLL;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import team12.workoutmadness.BE.Profile;
import team12.workoutmadness.BE.Workout;
import team12.workoutmadness.DAL.FirebaseDB;
import team12.workoutmadness.DAL.IWorkoutDB;
import team12.workoutmadness.DAL.WorkoutDB;

public class BLLManager
{
    private static BLLManager instance = null;
    private FirebaseDB firebase;
    private IWorkoutDB workoutDB;
    private BLLManager(Context c)
    {
        firebase = new FirebaseDB();
        workoutDB = new WorkoutDB(c);
    }

    public static BLLManager getInstance(Context c)
    {
        if (instance == null)
            instance = new BLLManager(c);

        return instance;
    }
    public void addWorkout(Workout workout) {
        workoutDB.addWorkout(workout);
    }
    public void addProfile(Profile profile) {workoutDB.addProfile(profile);}
    public void updateWorkout(Workout workout){workoutDB.updateWorkout(workout);}
    public void updateProfile(Profile profile){workoutDB.updateProfile(profile);}
    public ArrayList<Workout> getWorkouts(){
        return workoutDB.getWorkouts();
    }
    public Profile getProfile() { return workoutDB.getProfile(); }
    public int getNextID(){return workoutDB.getNextWorkoutID();}
    public void deleteWorkout(int workoutID){workoutDB.deleteWorkout(workoutID);}
    public FirebaseAuth getFirebaseAuth(){
        return firebase.getFirebaseAuth();
    }
    public String getFirebaseUsername(){
        String username = firebase.getFirebaseUser().getEmail();
        return username.substring(0,username.indexOf('@'));
    }

    public FirebaseUser getFirebaseUser() {
        return  firebase.getFirebaseUser();
    }
}