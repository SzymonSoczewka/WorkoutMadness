package team12.workoutmadness.DAL;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import team12.workoutmadness.BE.Profile;
import team12.workoutmadness.BE.Workout;

public interface IWorkoutDB {
    long addWorkout(Workout workout);
    long addProfile(Profile profile);
    void updateWorkout(Workout workout);
    void updateProfile(Profile profile);
    void deleteWorkout(int workoutID);
    ArrayList<Workout> getWorkouts();
    Profile getProfile();
    int getNextWorkoutID();

}
