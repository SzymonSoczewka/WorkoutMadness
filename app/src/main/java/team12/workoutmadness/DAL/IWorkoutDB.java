package team12.workoutmadness.DAL;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import team12.workoutmadness.BE.Workout;

public interface IWorkoutDB {
    long addWorkout(Workout workout);
    void updateWorkout(Workout workout);
    void deleteWorkout(int workoutID);
    ArrayList<Workout> getWorkouts();
    int getNextID();
}
