package team12.workoutmadness.Entity;

import java.util.List;

public class Day {
    private String name;
    private List<Exercise> exercises;

    public Day(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
