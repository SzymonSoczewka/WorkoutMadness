package team12.workoutmadness.models;

import java.util.ArrayList;
import java.util.List;

import team12.workoutmadness.models.Exercise;

public class Day {
    private String name;
    private ArrayList<Exercise> exercises = new ArrayList<>();

    public Day(String name) {
        this.name = name;
    }

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
