package team12.workoutmadness.DAL;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import team12.workoutmadness.BE.Day;
import team12.workoutmadness.BE.Workout;

public class FirebaseDB {
    private Day selectedDay = null;
    private Workout currentWorkout = null;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public void setWorkout(Workout workout) {
        currentWorkout = workout;
    }
    public Workout getCurrentWorkout(){
        return currentWorkout;
    }

    public void setSelectedDay(Day day) {
        selectedDay = day;
    }

    public void updateDay(Day day) {
        currentWorkout.updateDay(day);
    }

    public Day getSelectedDay() {
        return selectedDay;
    }

    public FirebaseAuth getFirebaseAuth(){
       return  firebaseAuth;
    }
    public FirebaseUser getFirebaseUser(){
        return firebaseAuth.getCurrentUser();
    }
}
