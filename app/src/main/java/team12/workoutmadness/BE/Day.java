package team12.workoutmadness.BE;

import java.io.Serializable;
import java.util.ArrayList;

public class Day implements Serializable {
    private String name;
    private ArrayList<Exercise> exercises = new ArrayList<>();

    public Day(String name) {
        this.name = name;
    }
    public Day(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }
}
